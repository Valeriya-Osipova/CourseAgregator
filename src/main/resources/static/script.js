async function submit() {
    let params = {}
    let school = document.getElementById('school_select').value;
    if (school != 'Школа') params['school'] = school
    let category = document.getElementById('category_select').value;
    if (category != 'Категория') params['category'] = category;
    let name = document.getElementById('search').value;
    params['name'] = name;
    let url = '/newSearch?' + new URLSearchParams(params).toString()
    let response = await fetch(url);
    let json = await response.json();
    console.log(json);
    if (json.length == 0) alert("Не найдено");
    let elems = document.getElementsByClassName("courses_element");
    while (elems.length !== 0){
        elems[0].remove()
    }
    json.forEach(course => {
        document.querySelector('#courses').innerHTML +=
            '<div class="courses_element">' +
            '<div class="right_place">' +
            '<div class="course_name_block">' +
            '<a class="course_name" target = "_blank" href="' + course['url'] + '">' + course['name'] + '</a>' +
            '</div>' +
            '<div class="school">' +
            '<img class="school_logo" src="' + course['photo'] + '" alt="">' +
            '<p class="school_name">' + course['school'] + '</p>' +
            '</div>' +
            '</div>' +
            '<div class="left_place">' +
            '<div class="top_part">' +
            '<span class="duration_cost">' + 'Продолжительность: ' + '</span>' +
            '<span class="duration_cost_value">' + course['duration'] + '</span>' +
            '</div>' +
            '<div id="rate" class="middle_part">'+
                '<span class="duration_cost">'+'Оценка пользователей:'+' </span>'+
                '<span class="duration_cost_value">'+ course['mark'] +'</span>'+
            '</div>' +
            '<div class="bottom_part">' +
            '<span class="duration_cost">' + 'Стоимость: ' + '</span>' +
            '<span class="duration_cost_value">' + course['cost'] + ' &#8381 </span>' +
            '</div>' +
            '</div>' +
            '<div class="feedback">'+
                '<a href="'+'feedback/' + course['id'] + '" >' +
                    '<button class="give_feedback">Оставить отзыв</button>'+
                '</a>'+
            '</div>'+
            '</div>'
    });

    let html = ``;
}