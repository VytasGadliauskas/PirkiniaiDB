<%
String klaida = "Nezinoma klaida";
if (!"".equals(request.getParameter("klaida"))) {
    klaida = request.getParameter("klaida");
} %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="js/scrypt.js"></script>
    <title>Klaida</title>
</head>
<body>
    <div class="d-flex flex-column align-items-center justify-content-center vh-100 bg-primary">
        <h1 class="display-1 fw-bold text-white">Klaida</h1>
        <p>
              <%=klaida%>
        </p>
        <a class="fw-bold text-white" href="index.jsp" >Bandyti dar karta ...</a>
    </div>
</body>
</html>