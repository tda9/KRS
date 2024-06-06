<%-- 
    Document   : settingList
    Created on : May 17, 2024, 9:11:33 PM
    Author     : ADMIN
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Knowledge Revising Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Premium Bootstrap 4 Landing Page Template" />
        <meta name="keywords" content="Appointment, Booking, System, Dashboard, Health" />
        <meta name="author" content="Shreethemes" />
        <meta name="email" content="support@shreethemes.in" />
        <meta name="website" content="../../../index.html" />
        <meta name="Version" content="v1.2.0" />
        <!-- favicon -->
        <link rel="shortcut icon" href="/krs/assets/images/favicon.ico.png">
        <!-- Bootstrap -->
        <link href="/krs/assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- simplebar -->
        <link href="/krs/assets/css/simplebar.css" rel="stylesheet" type="text/css" />
        <!-- Select2 -->
        <link href="/krs/assets/css/select2.min.css" rel="stylesheet" />
        <!-- Icons -->
        <link href="/krs/assets/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
        <link href="/krs/assets/css/remixicon.css" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- SLIDER -->
        <link href="/krs/assets/css/tiny-slider.css" rel="stylesheet" />
        <!-- css -->
        <link href="/krs/assets/css/style.min.css" rel="stylesheet" type="text/css" id="theme-opt" />
        
        </head>
    <body>
        <div id="preloader">
            <div id="status">
                <div class="spinner">
                    <div class="double-bounce1"></div>
                    <div class="double-bounce2"></div>
                </div>
            </div>
        </div>

        <div class="page-wrapper doctris-theme toggled">
            <jsp:include page="layout/menu.jsp"/>

            <main class="page-content bg-light">
                <div class="top-header">
                    <jsp:include page="layout/header.jsp"/>
                </div>

                <div class="container-fluid">
                    <div class="layout-specing">
                        <div class="d-md-flex justify-content-between">
                            <h5 class="mb-0">Setting Details</h5>

                            <nav aria-label="breadcrumb" class="d-inline-block mt-4 mt-sm-0">
                                <ul class="breadcrumb bg-transparent rounded mb-0 p-0">
                                    <li class="breadcrumb-item"><a href="/krs/SettingServlet">System Setting</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Details</li>
                                </ul>
                            </nav>
                        </div>
                        <a href="settingDetails.jsp"></a>

                        <div class="row">
                            <div class="row justify-content-around">
                                <div class="col-lg-6">
                                    <div class="rounded shadow bg-white-50 mt-4">
                                        <div class="p-4">                                                            
                                            <form action="SettingServlet" class="mt-4">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <div class="mb-3">
                                                            <label class="form-label">Name</label>
                                                            <input maxlength="20" name="name" id="name" type="text" class="form-control" required value="${settingDetails.getName()}">
                                                        </div>
                                                    </div><!--end col-->

                                                    <div class="col-md-6">
                                                        <div class="mb-3">
                                                            <label class="form-label">Type</label>
                                                            <input maxlength="20" name="type" type="text" class="form-control" required value="${settingDetails.getType()}">
                                                        </div>
                                                    </div><!--end col-->


                                                    <div class="col-md-6">
                                                        <div class="mb-3">
                                                            <label class="form-label">Order</label>
                                                            <input maxlength="2" max="99" name="order" type="number" class="form-control" required value="${settingDetails.getOrder()}" oninput="enforceMaxLength(event)">
                                                        </div>
                                                    </div><!--end col-->

                                                    <div class="col-md-6">                       
                                                        <label class="form-label">Status</label><br>
                                                        <div class="mt-2 d-flex justify-content-around">
                                                            <div><label for="html">Activate</label>
                                                                <input type="radio" name="status" ${settingDetails.isStatus()==false?"checked":""} value="false"></div>
                                                            <div><label for="html"  >Deactivate</label>
                                                                <input type="radio" name="status" ${settingDetails.isStatus()==true?"checked":""} value="true"></div>
                                                        </div>
                                                    </div><!--end col-->

                                                    <div class="col-md-12">
                                                        <div class="mb-3">
                                                            <label class="form-label">Description</label>
                                                            <textarea maxlength="255" name="description" id="comments" rows="3" class="form-control" value="${settingDetails.getDescription()}"  >${settingDetails.getDescription()}</textarea>
                                                        </div>
                                                    </div>
                                                </div><!--end row-->

                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <input type="submit" id="submit" class="btn btn-primary" value="Submit">
                                                        <input type="reset" class="btn btn-primary">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="setting_id" value="${settingDetails.getSetting_id()}">
                                                        ${warn}
                                                    </div><!--end col-->
                                                    
                                                </div><!--end row-->
                                            </form><!--end form-->
                                        </div>
                                    </div>                                                       
                                </div><!--end col-->                                               
                            </div><!--end row-->                                                        
                        </div><!--end row--> 
                    </div>

                </div>
            </main>
        </div>
        <script src="/krs/assets/js/bootstrap.bundle.min.js"></script>
        <!-- simplebar -->
        <script src="/krs/assets/js/simplebar.min.js"></script>
        <!-- Chart -->
        <script src="/krs/assets/js/apexcharts.min.js"></script>
        <script src="/krs/assets/js/columnchart.init.js"></script>
        <!-- Icons -->
        <script src="/krs/assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="/krs/assets/js/app.js"></script>  
        <script>
        function enforceMaxLength(event) {
            const input = event.target;
            if (input.value.length > 2) {
                input.value = input.value.slice(0, 2);
            }
        }
    </script>
 </body>
</html>
