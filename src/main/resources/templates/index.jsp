<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale-1.0">
    <link href="../../../main/resources/templates/bootstrap/css/bootstrap.min.css" th:href="@{bootstrap/css/bootstrap.min.css}" type="text/css" rel="stylesheet">
    <link href="../../../main/resources/templates/bootstrap/css/styles.css" th:href="@{bootstrap/css/styles.css}" type="text/css" rel="stylesheet">
    <title>System Księgowy</title>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a href="/" class="navbar-brand">Home</a>

            <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
                <span class="glyphicon glyphicon-list"></span>
            </button>

            <div class="collapse navbar-collapse navHeaderCollapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/">Główna</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <footer class="footer">
        <div class="container">
            <p class="navbar-text centered">Weekop developed by &copy; Wojciech Gauze Development</p>
        </div>
    </footer>

    <script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="../../../main/resources/templates/bootstrap/js/bootstrap.js" th:href="@{../templates/bootstrap/js/bootstrap.js}"></script>
</body>
</html>