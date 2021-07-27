-- user by name 
SELECT *
FROM users
WHERE users.full_name LIKE '';

-- course by title
SELECT *
FROM courses
WHERE courses.title LIKE '';

-- courses by category
SELECT courses.*
FROM courses
	JOIN categories ON courses.category = categories.id
WHERE category.title = '';

-- top 10 courses by rating
SELECT courses.*, rating.scores
FROM courses
	JOIN 
		(SELECT course_id, AVG(score) AS scores
        FROM rating 
        GROUP BY course_id)
	ON courses.id = rating.course_id
ORDER BY rating.scores DESC
LIMIT 10;

-- all modules of a course
SELECT *
FROM modules
WHERE modules.course_id = 42;

-- all topics of a module
SELECT *
FROM topics
WHERE topics.module_id = 42;

-- all tasks of a topic
SELECT *
FROM tasks
WHERE tasks.topic_id = 42;

-- all courses of a user
SELECT courses.*
FROM courses
	JOIN enrolled_courses ec ON courses.id = ec.course_id
WHERE ec.user_id = 42;

-- all users in course
SELECT users.*
FROM users
	JOIN enrolled_courses ec ON users.id = ec.user_id
WHERE ec.course_id = 42;