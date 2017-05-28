<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="cps" uri="CPS.TLD" %>
<%
	final String path = request.getContextPath();
	pageContext.setAttribute("path", path);
%>
        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Hello, ${sessionScope.username}</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="${path}/logout">
                        <i class="fa fa-sign-out"></i> <i class="fa fa-dot"></i>
                    </a>
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
					<ul class="nav" id="side-menu">
                        <li>
                            <a href="${path}/lookup"><i class="fa fa-dashboard fa-fw"></i> View</a>
                        </li>
                        <li>
                            <a href="${path}/search"><i class="fa fa-table fa-fw"></i> Search</a>
                        </li>
                        <cps:check-rights>
							<li>
                            	<a href="${path}/add"><i class="fa fa-bar-chart-o fa-fw"></i> Add</a>
                        	</li>
                        	<li>
                            	<a href="${path}/manage"><i class="fa fa-edit fa-fw"></i> Manage</a>
                        	</li>
                        </cps:check-rights>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
