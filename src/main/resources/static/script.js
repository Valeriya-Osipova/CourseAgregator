// let input = document.querySelector('#search');
// input.oninput = function(){
//     let val = this.value.trim();
//     let list = document.querySelectorAll('.course_name_block a');
//     let card = document.querySelectorAll('.courses_element');
//     if (val != ''){
//         list.forEach(function(elem){
//             if(elem.innerText.search(val) == -1){
//                 card.classList.add('hide');
//             }
//             else{
//                 card.classList.remove('hide');
//             }
//         });
//     };
//     console.log(this.val);
//     console.log('1');
// };

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
    if (json.length == 0) alert("Не найдено");
    document.querySelector('#courses').innerHTML = '';
    let element = document.getElementById("courses");
    while (element.firstChild) {
        element.removeChild(element.firstChild);
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
            '<div class="bottom_part">' +
            '<span class="duration_cost">' + 'Стоимость: ' + '</span>' +
            '<span class="duration_cost_value">' + course['cost'] + ' &#8381 </span>' +
            '</div>' +
            '</div>' +
            '</div>'
    });
}

async function search() {
    let name = document.getElementById('search').value;
    let url = '/newSearch?name=' + encodeURI(name);
    let response = await fetch(url);
    let json = await response.json();
    if (json.length == 0) alert("Не найдено");
    document.querySelector('#courses').innerHTML = ''
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
        '<div class="bottom_part">' +
        '<span class="duration_cost">' + 'Стоимость: ' + '</span>' +
        '<span class="duration_cost_value">' + course['cost'] + ' &#8381 </span>' +
        '</div>' +
        '</div>' +
        '</div>'
});
}
