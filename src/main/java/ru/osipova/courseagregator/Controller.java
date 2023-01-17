package ru.osipova.courseagregator;

import com.fasterxml.jackson.databind.util.JSONPObject;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.osipova.courseagregator.entity.*;
import ru.osipova.courseagregator.repositiories.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @GetMapping("/courses/all")
    @ResponseBody
    public Object getCourses(){
        return courseRepository.findAll();
    }

    @GetMapping(value = {"", "/courses"})
    public String filter(Model model){
        model.addAttribute("courses",getCoursesWithMarks(courseRepository.findAll(PageRequest.of(1, 20)).stream().toList()));
        return "courses";
    }

    @GetMapping("/courses/{id}")
    public Object course(@PathVariable Long id, Model model){
        model.addAttribute("cource", courseRepository.findById(id));
        model.addAttribute("feedbacks", feedbackRepository.findAllByCourseId(id));
        return "marks";
    }

    @GetMapping("/feedback/{id}")
//    @ResponseBody
    public Object giveFeedback(@PathVariable Long id, Model model){
        model.addAttribute("feedback", new Feedback());
        model.addAttribute("id", id);
        return "feedback";
    }

    @PostMapping("/feedback/{id}")
//    @ResponseBody
    public Object postFeedback(@PathVariable Long id, Model model, Feedback feedback){
        var userinfo = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        feedback.setUsername(((OidcUserAuthority)userinfo.toArray()[0]).getIdToken().getGivenName());
        feedback.setEmail(((OidcUserAuthority)userinfo.toArray()[0]).getIdToken().getEmail());
        feedback.setId(new Date().getTime());
        feedback.setCourseId(id);
        feedbackRepository.save(feedback);
        return "redirect:/";
    }

    @GetMapping("/filter")
    @ResponseBody
    public List<Course> filter(@RequestParam (required = false)  String school, @RequestParam (required = false) String category){
        System.out.println(school);
        System.out.println(category);
        if (school == null && category!=null){
            return courseRepository.findAllByCategory(category);
        } else if (school != null && category == null) {
            return courseRepository.findAllBySchool(school);
        } else{
            return courseRepository.findAllBySchoolAndCategory(school, category);
        }
    }
    @GetMapping("/search")
    @ResponseBody
    public List<Course> search(@RequestParam (required = false) String name){
        return courseRepository.findAllByNameContains(name);
    }

    @GetMapping("/newSearch")
    @ResponseBody
    public List<Course> newSearch(@RequestParam (required = false) Integer cost, @RequestParam (required = false) String name, @RequestParam (required = false) String category, @RequestParam (required = false) String school) {
        List<Course> src = courseRepository.findAll();
        List<Course> result = new ArrayList<>();
        for (Course course : src) {
            if (name != null){
                if (!course.getName().toLowerCase().contains(name.toLowerCase())){
                    continue;
                }
            }
            if (category != null){
                if (!course.getCategory().toLowerCase().contains(category.toLowerCase())){
                    continue;
                }
            }
            if (school != null){
                if (!course.getSchool().toLowerCase().contains(school.toLowerCase())){
                    continue;
                }
            }
            result.add(course);
        }

        return getCoursesWithMarks(result);
    }

    public List<Course> getCoursesWithMarks(List<Course> list){
        for (Course course : list) {
            course.setMark(getMark(course.getId()));
        }
        return list;
    }

    public Float getMark(Long id){
        Float result = 0f;
        var list = feedbackRepository.findAllByCourseId(id);
        if(list.size()==0)return 0f;
        for (Feedback feedback : list) {
            result+=feedback.getMark();
        }
        return result/list.size();
    }

    @GetMapping("/logout")
    public Object logout(){
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
    @GetMapping("/user")
    @ResponseBody
    public Object user(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

}
