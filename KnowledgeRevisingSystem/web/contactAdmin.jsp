<%-- 
    Document   : contactAdmin
    Created on : May 17, 2024, 8:51:55 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <style>
            body::before {
                content: "";
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.3); /* Semi-transparent black overlay */
                z-index: 1;
            }
            /* CSS để căn giữa chiều dọc */
            body, html {
                height: 100%;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            body{
                background-image: url("assets/images/contactBG.jpg");
                background-size: cover;
                background-position: center;
                background-repeat: no-repeat;
               
            }

            .custom-border {
                border: 2px solid white; /* Border độ dày 2px màu đỏ */
                border-radius: 5px; /* Bo tròn góc */
            }
            .custom-form {
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.4); /* Thêm box shadow với màu sắc và độ mờ cụ thể */

            }
            
            .z-index-2{
                z-index: 2;
            }
            .back-button {
            position: absolute;
            top: 20px;
            left: 20px;
            z-index: 3; /* Ensure it's above the overlay */
            border-radius: 50%;
          
        }
        </style>
        
        <a href="homepage.jsp" class="btn back-button">
            <img src="assets/images/home-icon.png" height="50px" alt="home"/>
        </a>
        <div class="container p-5 bg-white custom-border custom-form z-index-2">
            <div class="row"> 
                <form action="ContactServlet?action=createContactMessage" method="post">
                    <div class="row">

                        <div class="form-group col-md-12">
                            <h3>Contact Admin</h3>
                            <i><p>We will respond within 48 hours!</p></i>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group col-md-12">
                            <label for="exampleInputEmail1">Full name <span class="text-danger">*</span></label>
                            <input type="text" name="fullName" required value="${user.getFullName()}" class="form-control" >
                        </div>

                    </div>
                    <div class="row">
                        <div class="form-group col-md-6">
                            <label for="exampleInputEmail1">Email <span class="text-danger">*</span></label>
                            <input type="email" value="${user.getEmail()}" required class="form-control" name="email">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="exampleInputPassword1">Phone <span class="text-danger">*</span></label>
                            <input type="text" value="${user.getPhone()}" required class="form-control" name="phone">
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label for="exampleInputEmail1">Title <span class="text-danger">*</span></label>
                            <input type="text" class="form-control" required name="subject">
                        </div>

                    </div>
                    <div class="row">
                        <div class="form-group col-md-12">
                            <label for="exampleInputEmail1">Message <span class="text-danger">*</span></label>
                            <textarea class="form-control" required name="message" rows="3" placeholder="Enter your messages here"></textarea>
                        </div>

                    </div>

                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>

            </div>
        </div>


    </body>
</html>
