function buildCourseBanner() {
    var dataFile = "./js/CourseData.json";
    loadData(dataFile, courseInfo);
}

function loadData(jsonFile, callback) {
    $.getJSON(jsonFile, function (json) {
        callback(json);
    });
}

function courseInfo(json) {
    addCourseData(json);
    addAuthor(json);
}

function addCourseData(data) {
    var courseHeader = $("#banner");
    for (var i = 0; i < data.banner.length; ) {
        var text = "";
        text += buildCourseData(data.banner[i]);
        i++;

        courseHeader.append(text);
    }
}
function buildCourseData(course) {

    var subject = course.subject;
    var semester = course.semester;
    var course_number = course.course_number;
    var course_year = course.course_year;
    var course_title = course.course_title;

    var text = subject + " " + course_number + " ~ " + semester + " " + course_year
            +"<br/>" + course_title + " ~ " + "02";
   //var text = "<strong>hello</strong>"
    return text;
}

function addAuthor(data) {
    var author = $("#instructor_link");
    for (var i = 0; i < data.instructor_link.length; ) {
        var text = "";
        text += buildAuthorData(data.instructor_link[i]);
        i++;

        author.append(text);
    }
}

function buildAuthorData(author) {

    var name = author.instructor_name;
    var home = author.instructor_home;

    var text = "<a href="+home+">"+name+"</a>";

    return text;
}
