package ru.osipova.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.osipova.demo1.entity.Course;
import ru.osipova.demo1.repositiories.CourseRepository;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private CourseRepository courseRepository;
    @GetMapping("/courses/all")
    @ResponseBody
    public Object getCourses(){
        return courseRepository.findAll();
    }

    @GetMapping("/courses")
    public String filter(Model model){
        model.addAttribute("courses",courseRepository.findAll());
        return "courses";
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
//            boolean add = true;
            if (name != null){
                if (!course.getName().toLowerCase().contains(name.toLowerCase())){
//                    add = false;
                    continue;
                }
            }
            if (category != null){
                if (!course.getCategory().toLowerCase().contains(category.toLowerCase())){
//                    add = false;
                    continue;
                }
            }
            if (school != null){
                if (!course.getSchool().toLowerCase().contains(school.toLowerCase())){
//                    add = false;
                    continue;
                }
            }
            if (cost != null){

                switch (cost){
                    case 0:
                        System.out.println(Integer.parseInt(course.getCost()));
                        if (Integer.parseInt(course.getCost()) != 0){
                            continue;
                        } break;
                    case 1:
                        if (Integer.parseInt(course.getCost()) >= 10000 || Integer.parseInt(course.getCost()) == 0){
                            continue;
                        } break;
                    case 2:
                        if (Integer.parseInt(course.getCost()) >= 20000 || Integer.parseInt(course.getCost()) < 10000){
                            continue;
                        }break;
                    case 3:
                        if (Integer.parseInt(course.getCost()) >= 50000 || Integer.parseInt(course.getCost()) < 20000){
                            continue;
                        }break;
                    case 4:
                        if (Integer.parseInt(course.getCost()) < 50000){
                            continue;
                        } break;
                }
            }
//            if (add){
            result.add(course);
//            }
        }

        return result;
    }
}
