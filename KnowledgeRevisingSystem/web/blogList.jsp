<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Blog Layout</title>
        <link rel="stylesheet" href="assets/css/bootstrap.min.css"/>
        <style>
            .search-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin: 20px 0;
            }
            .search-bar-input {
                width: 70%;
                padding: 10px;
                font-size: 16px;
            }
            .search-bar-input input{
                border-radius: 5px;
                width: 80%;
                padding: 10px 20px;
            }
            .search-bar button {
                margin-bottom: 20px;
                padding: 10px 15px;
                background-color: #f4f4f4;
                border: 1px solid #ccc;
                border-radius: 5px;
                text-decoration: none;
                color: #333;
                cursor: pointer;
            }
            .search-bar button:hover{
                background-color: #e9e9e9;
            }
            .blogs {
                display: flex;
                flex-wrap: wrap;
                gap: 20px;
                justify-content: space-between;
            }
            .blog-card {
                width: 25%;
                background-color: #f4f4f4;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                text-align: center;
                margin-bottom: 20px;
                position: relative;
            }
            .blog-card img {
                width: 90%;
                height: auto;
            }
            .blog-card h3 {
                font-size: 18px;
                margin: 10px 0;
            }
            .blog-card p {
                font-size: 14px;
                color: #555;
            }
            .blog-card a{
                position: absolute;
                bottom: 5px;
                transform: translateX(-50%);
            }
            @media (max-width: 768px) {
                .blog-card {
                    flex: 1 1 calc(50% - 20px);
                }
            }
            @media (max-width: 480px) {
                .blog-card {
                    flex: 1 1 100%;
                }
                .search-bar input {
                    width: 70%;
                }
                .search-bar button {
                    width: 25%;
                }
            }
        </style>
    </head>
    <body>
        <jsp:include page="layout/header.jsp"/>
        <div class="container">
            <div class="search-bar">
                <button onclick="window.location.href='homepage.jsp'">&larr; Back</button>
                <div class="search-bar-input">
                    <form action="BlogServlet">
                        <input type="text" name="query" placeholder="Search for blogs..." value="${query}">
                        <button type="submit">Search</button>
                    </form>
                </div>
                <button>Filter</button>
            </div>
            <div class="blogs">
                <c:forEach items="${blogList}" var="i">
                    <div class="blog-card">
                        <img src="assets/images/blog/${i.getThumbnail_url()}" alt="Getting Started">
                        <h3>${i.getTitle()}</h3>
                        <p>${i.getSummary()}</p>
                        <a href="BlogServlet?blogId=${i.getPost_id()}">View more...</a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>