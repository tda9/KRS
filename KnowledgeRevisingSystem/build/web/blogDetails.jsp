<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Blog Detail</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                padding: 20px;
                box-sizing: border-box;
            }
            .container {
                max-width: 800px;
                margin: 20px auto;
                border: 1px solid #ccc;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }
            .back-button {
                display: inline-block;
                margin-bottom: 20px;
                padding: 10px 15px;
                background-color: #f4f4f4;
                border: 1px solid #ccc;
                border-radius: 5px;
                text-decoration: none;
                color: #333;
                cursor: pointer;
            }
            .back-button:hover {
                background-color: #e9e9e9;
            }
            .blog-title {
                font-size: 24px;
                margin-bottom: 20px;
                color: #333;
            }
            .blog-image {
                width: 50%;
                height: auto;
                margin: 20px auto;
                transform: translateX(50%);
            }
            .blog-content {
                font-size: 16px;
                color: #555;
                line-height: 1.6;
            }
            .signature {
                margin-top: 20px;
                font-size: 16px;
                color: #333;
            }
        </style>
    </head>
    <body>
        <jsp:include page="layout/header.jsp"/>
        <div class="container">
            <c:set var="x" value="${blogDetail}"></c:set>
            <button onclick="history.back()" class="back-button">&larr; Back</button>
            <h1 class="blog-title">${x.getTitle()}</h1>
            <img src="assets/images/blog/${x.getThumbnail_url()}" alt="Blog Details Image" class="blog-image">
            <div class="blog-content">
                <p>${x.getContent()}</p>
            </div>
            <p class="signature">
                Best regards,<br>
                Vu Khanh Hoang
            </p>
        </div>
    </body>
</html>