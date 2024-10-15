# Quiz Project

This monolithic project demonstrates the implementation of generating questions for quizzes using Spring Boot. 
The application focuses on providing a structured approach to creating and managing quiz questions within a single codebase. <br>
The project is designed to practice and enhance skills in:

    Java Streams and Lambda Expressions
    Spring Boot
    Liquibase for database versioning

## Project Overview: <br>
1) Services:


    Quiz Service: Manages quizzes, including the list of questions for each quiz and the calculation of quiz results.
    Question Service: Manages individual questions and their details.

2) Database Structure


    Both services share a single database.

3) Technologies Used


    Spring Boot for building the microservices.
    Spring Data JPA for data persistence.
    PostgreSQL as the database.
    StructMap for mapping.
    Liquibase for database migrations.


## SQL Scripts
1) Database Creation

`CREATE DATABASE quizapp_db;`

2) Insert Data <br>

`INSERT INTO question
(category, difficulty_level, option1, option2, option3, option4, question_title, right_answer)
VALUES
('Java', 'Easy', 'class', 'interface', 'extends', 'implements', 'Which Java keyword is used to create a subclass?', 'extends'),
('Java', 'Easy', '4', '5', '6', 'Compile error', 'What is the output of the following Java code snippet? \nint x = 5; \nSystem.out.println(x++);', '5'),
('Java', 'Easy', 'TRUE', 'FALSE', '0', 'null', 'In Java, what is the default value of an uninitialized boolean variable?', 'FALSE'),
('Java', 'Easy', 'try', 'throw', 'catch', 'finally', 'Which Java keyword is used to explicitly throw an exception?', 'throw'),
('Java', 'Easy', 'It indicates that a variable is constant.', 'It indicates that a method can be accessed without creating an instance of the class.', 'It indicates that a class cannot be extended.', 'It indicates that a variable is of primitive type.', 'What does the ''static'' keyword mean in Java?', 'It indicates that a method can be accessed without creating an instance of the class.'),
('Java', 'Easy', 'constant int x = 5;', 'final int x = 5;', 'static int x = 5;', 'readonly int x = 5;', 'What is the correct way to declare a constant variable in Java?', 'final int x = 5;'),
('Java', 'Easy', 'for loop', 'while loop', 'do-while loop', 'switch loop', 'Which loop in Java allows the code to be executed at least once?', 'do-while loop'),
('Java', 'Easy', 'To terminate a loop or switch statement and transfer control to the next statement.', 'To skip the rest of the code in a loop and move to the next iteration.', 'To define a label for a loop or switch statement.', 'To check a condition and execute a block of code repeatedly.', 'What is the purpose of the ''break'' statement in Java?', 'To terminate a loop or switch statement and transfer control to the next statement.'),
('Java', 'Easy', '+', '-', '*', '/', 'Which Java operator is used to concatenate two strings?', '+'),
('Java', 'Easy', 'HashMap', 'ArrayList', 'LinkedList', 'HashSet', 'In Java, which collection class provides an implementation of a dynamic array?', 'ArrayList'),
('Python', 'Easy', 'count()', 'size()', 'length()', 'len()', 'Which Python function is used to calculate the length of a list?', 'len()'),
('Python', 'Easy', '[1, 2, 3]', '[1, 2, 3, 4]', '[4, 3, 2, 1]', 'Error', 'What is the output of the following Python code snippet? \nx = [1, 2, 3] \nx.append(4) \nprint(x)', '[1, 2, 3, 4]'),
('Python', 'Easy', 'break', 'continue', 'pass', 'return', 'Which Python statement is used to exit from a loop prematurely?', 'break'),
('Python', 'Easy', 'To generate a random number within a given range.', 'To iterate over a sequence of numbers.', 'To sort a list in ascending order.', 'To calculate the length of a string.', 'What is the purpose of the ''range()'' function in Python?', 'To iterate over a sequence of numbers.'),
('Python', 'Easy', 'int', 'float', 'str', 'list', 'In Python, which data type is mutable?', 'list'),
('Python', 'Easy', 'datetime', 'math', 'os', 'sys', 'Which Python module is used for working with dates and times?', 'datetime')
;`

## API Endpoints
http://localhost:8080
1) Quiz Service

    `POST /quiz/create`: Create a new quiz. <br>
    `GET /quiz/{id}`: Retrieve a quiz by its ID. <br>
    `POST /quiz/submit/{id}`: Submit responses and calculate the result.

2) Question Service

    `GET /queston/all:` Retrieve all questions. <br>
    `GET /question/category/{cat}:` Retrieve questions by category. <br>
    `POST /question/add:` Create new question. <br>
    `PUT /question/edit/{id}:` Edit question details. <br>
    `DELETE /question/delete/{id}:` Remove a question by its ID. <br>
