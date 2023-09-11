# Online Bookstore Project

This project is an online bookstore system that provides users with an enhanced shopping experience.

## Setup

To run this project locally
1.Clone this repository:
git clone https://github.com/yourusername/online-bookstore.git 
you can use Intellij Software for running the project.

SDK: 1.8 Oracle OpenJDK version
Language Level: SDK Default
Run using MVN Software
Firstly you have to use command "mvn clean:install"
After that you can edit configuration by using command as jetty:run

Access the API at
http://localhost:9000/onlineBookStore/ => OnlineBookStore Page
http://localhost:9000/onlineBookStore/site/login => Login the site
http://localhost:9000/onlineBookStore/site/signup => SignUp the site
http://localhost:9000/onlineBookStore/site/home => Home Page
http://localhost:9000/onlineBookStore/ui/book => Book Page
http://localhost:9000/onlineBookStore/ui/createOrder => Create Order
http://localhost:9000/onlineBookStore/ui/cart => Cart
[http://localhost:9000/onlineBookStore/ui/order => Order
http://localhost:9000/onlineBookStore/ui/admin => Admin
http://localhost:8085/fop/ => Invoice App

You can use swagger or postman to check GET,POST,PUT,DELETE request on these APIs


## API Documentation

http://localhost:9000/onlineBookStore/ => OnlineBookStore Page
THis will be the home page of our given website which will tell in brief about our website.

http://localhost:9000/onlineBookStore/site/signup => SignUp the site
On this API we can signup on the page and I have validated that user will not enter empty username and password length should be more than or equal to 8 characters
including one uppercase,one lowercase,one numerical and one special character.
I have given supervisor role to 5 specific person and they will have access of admin page.

http://localhost:9000/onlineBookStore/site/login => Login the site
After signup on the page, we can login our website and after logging on the website, we will redirect to home page of our website. User can login only if he has
signUp with given email and password. Supervisor have access to admin page and he can delete operator from admin page but operator will not have access to admin
page.

http://localhost:9000/onlineBookStore/site/home => Home Page
Home Page will describe about our website. In navigation bar we have Book,Create Order,View Cart,View Order,Admin. We can redirect to that api which we want to go 
after clicking on button in navigation bar.

http://localhost:9000/onlineBookStore/ui/book => Book Page
Book page mainly contains 5 input fields as Title,Author,Genre,Price and Availability. I have applied cases that no field should be empty. Also, Title and Author 
combination must be unique. I have added datatables to display data and in search button you can search only on the basis of title,author and genre. User can add,
update and delete the book based on his requirements.

http://localhost:9000/onlineBookStore/ui/createOrder => Create Order Page
Create Order Page will display the all books in table format and user can search on the basis of title, author ,genre, minimumPrice and maximumPrice.User can click
on add and remove to modify changes and he can see it in cart.

http://localhost:9000/onlineBookStore/ui/cart => Cart
Cart page will tell about the books purchased by the user.User can add or remove books from cart page also. On this page, he will see the total price of same type
of books and total price also. If user remove book from the cart when its quantity is 1, then that book will be removed from the cart. After clicking on place Order
button,cart will become empty and user can go to viewOrder page to check order based on its orderId by clicking on ViewOrder button in navBar.

http://localhost:9000/onlineBookStore/ui/order => Order
Order Page will tell about the order done by user and each orderId tells about the orderItem containing different books ordered by different user with total price
of each speicific book and total price of all books.

http://localhost:9000/onlineBookStore/ui/admin => Admin
Admin page will be visible by SUPERVISOR only and he can delete operator but he can not delete other supervisor.
I have fixed number of supervisors in application.properties

This is all about my website and you can use swagger or postman to check backend part of my website.
