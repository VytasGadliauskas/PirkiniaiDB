<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="lt.bit.prekes.data.Cekis"%>
<%@page import="lt.bit.prekes.data.Tipas"%>
<%@page import="java.text.ParseException"%>
<%@page import="jakarta.persistence.Query"%>
<%@page import="jakarta.persistence.EntityManager"%>

<%
    List<Cekis> cekiai = null;
    Set<String> parduotuves = null;
    Map<Integer,Double> cekisSuma = new HashMap<>();
    List<Tipas> tipai = null;
    Map<Integer,String> tipaiMap = new HashMap<>();
    double visaSuma = 0.0;

    EntityManager em = (EntityManager) request.getAttribute("em");

    if (em != null) {
         if ((!"".equals(request.getParameter("data_nuo")) && request.getParameter("data_nuo") != null) &&
             (!"".equals(request.getParameter("data_iki")) && request.getParameter("data_iki") != null)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date data_nuo = null;
                    try {
                        data_nuo = sdf.parse(request.getParameter("data_nuo"));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    Date data_iki = null;
                    try {
                        data_iki = sdf.parse(request.getParameter("data_iki"));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    Query qc1 = em.createQuery("SELECT c FROM Cekis c WHERE c.data >= :datanuo AND c.data <= :dataiki");
                    qc1.setParameter("datanuo", data_nuo);
                    qc1.setParameter("dataiki", data_iki);
                    cekiai =  qc1.getResultList();
                    Query qvisasuma1 = em.createNativeQuery("select sum(`Prekes`.`kiekis`*`Prekes`.`kaina`) as suma from Cekis, Prekes  WHERE Cekis.id=Prekes.cekis_id AND (Cekis.data >= :datanuo AND Cekis.data <= :dataiki)");
                    qvisasuma1.setParameter("datanuo", data_nuo);
                    qvisasuma1.setParameter("dataiki", data_iki);
                    visaSuma = Double.parseDouble(qvisasuma1.getSingleResult().toString());
                    System.out.println("filtras yra");
         } else {
                    Query qc2 = em.createQuery("select c from Cekis c");
                    cekiai =  qc2.getResultList();
                    Query qvisasuma2 = em.createNativeQuery("select sum(`kiekis`*`kaina`) as suma  from Prekes");
                    visaSuma = Double.parseDouble(qvisasuma2.getSingleResult().toString());
                    System.out.println("filtras nera");
         }
       Query qtipai = em.createQuery("select t from Tipas t");
       tipai =  qtipai.getResultList();
       parduotuves = new HashSet<>();
       for (Cekis cekis : cekiai) {
             parduotuves.add(cekis.getParduotuve());
             Query qvisasumacekio = em.createNativeQuery("select sum(kiekis*kaina) as suma from Prekes WHERE `cekis_id`=:cekisid");
             qvisasumacekio.setParameter("cekisid", cekis.getId());
             double visaSumaCekio = 0.0;
             if (qvisasumacekio.getSingleResult() != null){
                visaSumaCekio = Double.parseDouble(qvisasumacekio.getSingleResult().toString());
             }
             cekisSuma.put(cekis.getId(),visaSumaCekio);
       }
    }
%>  

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <script src="js/bootstrap.bundle.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="js/scrypt.js"></script>
    <title>Pirkiniai</title>
</head>
<body>
    <nav class="mt-5">
      <ul class="nav nav-pills justify-content-center">
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false">
            <img src="img/report.png" alt="date"width="40" height="42">Ataskaitos</a>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#ataskaita1Modal" href="#ataskaita1Modal">
                <img src="img/shopping-cart-with-money.png" data-bs-toggle="modal" data-bs-target="#ataskaita1Modal" alt="ataskaita1" width="40" height="42">
                 Bendra pirkini?? suma u?? period??</a></li>
            <li><a class="dropdown-item" data-bs-toggle="modal" data-bs-target="#ataskaita2Modal" href="#ataskaita2Modal">
                <img src="img/text-width.png" data-bs-toggle="modal" data-bs-target="#ataskaita2Modal" alt="ataskaita2" width="40" height="42">
                 Pirkiniai pagal tip?? u?? period??</a></li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" data-bs-toggle="modal" data-bs-target="#filtrasModal" href="#filtrasModal">
            <img src="img/filter.png" data-bs-toggle="modal" data-bs-target="#filtrasModal" alt="date"width="40" height="42">Filtras</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" data-bs-toggle="modal" data-bs-target="#aboutModal" href="#">
            <img src="img/about.png" data-bs-toggle="modal" data-bs-target="#aboutModal" alt="About" width="40" height="42">Apie </a>
        </li>
      </ul>
    </nav>

    <main class="d-flex flex-column flex-nowrap align-items-center pt-2">
        <div class="border border-primary">
            <table class="table">
                <thead>
                  <tr class="table-primary ">
                    <th scope="col text-center">
                        <img class="imgBtn" src="img/add-shopping-cart.png" onclick="addCekisModalF()" alt="Add" width="40" height="42">
                    </th>
                    <th scope="col"><img src="img/numbered-list.png" alt="date"width="40" height="42"></th>
                    <th scope="col"><img src="img/schedule.png" alt="date"width="40" height="42">&nbsp Data</th>
                    <th scope="col"><img src="img/shop.png" alt="shop"width="40" height="42">&nbsp Parduotuv??</th>
                    <th scope="col"><img src="img/about.png" alt="shop"width="40" height="42">&nbsp Aprasymas</th>
                    <th scope="col"><img src="img/money-bag.png" alt="money"width="40" height="42">&nbsp Bendra ??ekio suma</th>
                  </tr>
                </thead>
                <tbody>
                <% if (cekiai != null ) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        for (Cekis cekis : cekiai) {
                        %>
                      <tr>
                         <td><img class="imgBtn" src="img/clear-shopping-cart.png" onclick="deleteCekisModalF(this, <%=cekis.getId()%>)" alt="Delete" width="40" height="42">&nbsp
                            <img class="imgBtn" src="img/shopping-cart.png" onclick="editCekisModalF(this, <%=cekis.getId()%>)" alt="Edit" width="40" height="42"> </td>
                           <th class="tablecekisid" scope="row"><%=cekis.getId()%></th>
                           <td class="tablecekisdata"><%=sdf.format(cekis.getData())%></td >
                           <td class="tablecekisparduotuve"><%=cekis.getParduotuve()%></td>
                           <td class="tablecekisaprasymas"><%=cekis.getAprasymas()%></td>
                           <td class="tablecekissuma"><%=cekisSuma.get(cekis.getId())%> <img src="img/euro.png" alt="euro"width="40" height="42"></td>
                      </tr>
                   <%  }
                   } else { %>
                   <tr>
                     <td>N??ra ira????</td>
                     <td></td>
                     <td></td>
                     <td></td>
                     <td></td>
                     <td></td>
                     </tr>
                  <% } %>
                  <tr class="table-info">
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <th>
                        <%=visaSuma%>&nbsp
                        <img src="img/euro.png" alt="euro"width="40" height="42">
                    </th>
                  </tr>
                </tbody>
            </table>
        </div>  
    </main>
    <footer class="text-center pt-2 pb-5">
          <a  href="index.jsp"><img class="imgBtn" src="img/replay.png" alt="Refresh" width="40" height="42"></a>
    </footer> 
  
<!---------------------------------- Modal Add cekis -->
<div class="modal fade" id="addCekisModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="addCekisModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
     <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="addCekisModalLabel">PRID??TI ??EKI</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" >
            <img class="imgBtn" onclick="closeAddCekisModalF()" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
          <form action="addCekis" method="POST">
            <table class="table border border-primary border-2">
                <thead>
                  <tr class="table-primary rounded-top">
                    <th scope="col"><img src="img/schedule.png" alt="date"width="40" height="42">&nbsp Data</th>
                    <th scope="col"><img src="img/shop.png" alt="shop"width="40" height="42">&nbsp Parduotuv??</th>
                    <th scope="col"><img src="img/about.png" alt="shop"width="40" height="42">&nbsp Aprasymas</th>
                    <th scope="col"><img src="img/buy.png" alt="money"width="40" height="42">&nbsp Prekes </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td><input type="date" class="form-control" name="data"></td>
                    <td>
                        <input type="text" class="form-control" name="pavadinimas" list="parduotuves">
                           <datalist id="parduotuves">
                             <% for (String ppp : parduotuves) {  %>
                                   <option value="<%=ppp%>"><%=ppp%></option>
                             <% } %>
                           </datalist>
                    </td>
                    <td>
                    <textarea name="aprasymas" class="form-control" rows="5" cols="20"></textarea>
                    </td>
                    <td><img class="imgBtn" src="img/ingredients.png" alt="money"width="40" height="42"></td>
                  </tr>
                </tbody>
            </table>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" src="img/close.png" onclick="closeAddCekisModalF()"  alt="Close" width="40" height="42">
          </button>
          <button type="submit" class="btn btn-primary">
            <img class="imgBtn" src="img/ok.png"  alt="Ok" width="40" height="42">
          </button>
         </form>
        </div>
      </div>
    </div>
  </div>

<!---------------------------------- Modal Edit cekis -->
<div class="modal fade" id="editCekisModal" data-bs-backdrop="static" data-bs-keyboard="false"  tabindex="-1" aria-labelledby="editCekisModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="editCekisModalLabel">REDAGUOTI ??EKI</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" onclick="closeEditCekisModalF()" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
          <form action="editCekis" method="POST">
            <input type="hidden" id="editcekisid" name="id">
            <table class="table border border-primary border-2">
                <thead>
                  <tr class="table-primary rounded-top">
                    <th scope="col"><img src="img/schedule.png" alt="date"width="40" height="42">&nbsp Data</th>
                    <th scope="col"><img src="img/shop.png" alt="shop"width="40" height="42">&nbsp Parduotuv??</th>
                    <th scope="col"><img src="img/about.png" alt="about"width="40" height="42">&nbsp Aprasymas</th>
                    <th scope="col"><img src="img/buy.png" alt="money"width="40" height="42">&nbsp Prekes </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td><input type="date" class="form-control" id="editcekisdata" name="data"></td>
                    <td>
                        <input type="text" class="form-control" id="editcekisparduotuve" name="parduotuve" list="parduotuves" />
                           <datalist id="parduotuves">
                               <% for (String ppp : parduotuves) {  %>
                                     <option value="<%=ppp%>"><%=ppp%></option>
                               <% } %>
                           </datalist>
                    </td>
                     <td>
                     <textarea id="editcekisaprasymas" class="form-control" name="aprasymas"  rows="5" cols="20"></textarea>
                     </td>
                    <td><img class="imgBtn" onclick="editPrekesModalF(this)" src="img/ingredients.png" alt="money"width="40" height="42"></td>
                  </tr>
                </tbody>
            </table>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn" data-bs-dismiss="modal">
                <img class="imgBtn" onclick="closeEditCekisModalF()" src="img/close.png" alt="Close" width="40" height="42">
              </button>
              <button type="submit" class="btn btn-primary">
                <img class="imgBtn" src="img/ok.png" alt="Ok" width="40" height="42">
              </button>
           </form>
        </div>
      </div>
    </div>
  </div>

<!--------------------------------- Modal Delete cekis -->
<div class="modal fade" id="deleteCekisModal" data-bs-backdrop="static" data-bs-keyboard="false"  tabindex="-1" aria-labelledby="deleteCekisModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="deleteCekisModalLabel">I??TRINTI ??EKI</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" onclick="closeDeleteCekisModalF()" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
         <form action="deleteCekis" method="POST">
           <input type="hidden" id="deletecekisid" name="id" size="10">
            <table class="table border border-primary border-2">
                <thead>
                  <tr class="table-primary rounded-top">
                    <th scope="col"><img src="img/schedule.png" alt="date"width="40" height="42">&nbsp Data</th>
                    <th scope="col"><img src="img/shop.png" alt="shop"width="40" height="42">&nbsp Parduotuv??</th>
                    <th scope="col"><img src="img/about.png" alt="shop"width="40" height="42">&nbsp Aprasymas</th>
                    <th scope="col"><img src="img/buy.png" alt="money"width="40" height="42">&nbsp Prekes </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td id="deletecekisdata" name="data"></td>
                    <td id="deletecekisparduotuve" name="parduotuve"></td>
                    <td id="deletecekisaprasymas" name="aprasymas"></td>
                    <td><img src="img/ingredients.png" alt="money"width="40" height="42"></td>
                  </tr>
                </tbody>
            </table>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn" data-bs-dismiss="modal">
                <img class="imgBtn" onclick="closeDeleteCekisModalF()" src="img/close.png" alt="Close" width="40" height="42">
              </button>
              <button type="submit" class="btn btn-primary">
                <img class="imgBtn" src="img/clear-shopping-cart.png" alt="Delete" width="40" height="42">
              </button>
           </form>
        </div>
      </div>
    </div>
  </div>

  <!--------------------------------- Modal About -->
  <div class="modal fade" id="aboutModal" data-bs-keyboard="false"  tabindex="-1" aria-labelledby="aboutModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="aboutModalLabel">Apie</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
            <img src="img/pirkiniai.jpg" alt="Pirkiniai" width="420" height="420">
        </div>
        <div class="modal-footer">
        </div>
      </div>
    </div>
  </div>

<!---------------------------------- Modal Edit prekesSSSS -->
<div class="modal fade" id="editPrekesModal" data-bs-backdrop="static" data-bs-keyboard="false"  tabindex="-1" aria-labelledby="editPrekesModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-lg">
      <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="editPrekesModalLabel">REDAGUOTI PREKES</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" onclick="closeEditPrekesModalF()" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
           <div class="table" id="prekesList">
           </div>
        <div class="modal-footer">
            <button type="button" class="btn" data-bs-dismiss="modal">
                <img class="imgBtn" onclick="closeEditPrekesModalF()" src="img/close.png" alt="Close" width="40" height="42">
              </button>
           </form>
        </div>
      </div>
    </div>
  </div>

<!---------------------------------- Modal Add preke -->
<div class="modal fade" id="addPrekeModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="addPrekeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl">
     <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="addCekisModalLabel">PRID??TI PREK??</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" >
            <img class="imgBtn" onclick="closeAddPrekeModalF()" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
          <form action="addPreke" method="POST">
             <input type="hidden" id="addprekecekisid" name="cekis_id">
            <table class="table border border-primary border-2">
                <thead>
                  <tr class="table-primary rounded-top">
                    <th scope="col"><img src="img/hamper.png" alt="hamper" width="40" height="42">&nbsp Prek??</th>
                    <th scope="col"><img src="img/numbers.png" alt="numbers" width="40" height="42">&nbsp Kiekis</th>
                    <th scope="col"><img src="img/cash.png" alt="cash"width="40" height="42">&nbsp Kaina </th>
                    <th scope="col"><img src="img/type.png" alt="type"width="40" height="42">&nbsp Tipas </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td><input type="text" class="form-control" name="preke" required></td>
                    <td><input type="number" class="form-control" name="kiekis" min="0" step="any" required></td>
                    <td><input type="number" class="form-control" name="kaina" min="0" step="any" required></td>
                    <td><select class="form-select" id="prekeAddTipai" name="tipas_id" required>
                         <option selected></option>
                         <%  for (Tipas tipas : tipai) { %>
                         <option value="<%=tipas.getId()%>"><%=tipas.getPavadinimas()%></option>
                         <% } %>
                         </select>
                    </td>
                  </tr>
                </tbody>
            </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" src="img/close.png" onclick="closeAddPrekeModalF()"  alt="Close" width="40" height="42">
          </button>
          <button type="submit" class="btn btn-primary">
            <img class="imgBtn" src="img/ok.png"  alt="Ok" width="40" height="42">
          </button>
         </form>
        </div>
      </div>
    </div>
  </div>

<!---------------------------------- Modal Edit preke -->
<div class="modal fade" id="editPrekeModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="editPrekeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl">
     <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="editCekisModalLabel">REDAGUOTI PREK??</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" >
            <img class="imgBtn" onclick="closeEditPrekeModalF()" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
          <form action="editPreke" method="POST">
            <input type="hidden" id="editprekeid" name="id">
            <table class="table border border-primary border-2">
                <thead>
                  <tr class="table-primary rounded-top">
                    <th scope="col"><img src="img/hamper.png" alt="hamper" width="40" height="42">&nbsp Prek??</th>
                    <th scope="col"><img src="img/numbers.png" alt="numbers" width="40" height="42">&nbsp Kiekis</th>
                    <th scope="col"><img src="img/cash.png" alt="cash"width="40" height="42">&nbsp Kaina </th>
                    <th scope="col"><img src="img/type.png" alt="type"width="40" height="42">&nbsp Tipas </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td><input type="text" class="form-control" id="editprekepreke" name="preke" required></td>
                    <td><input type="number" class="form-control" id="editprekekiekis" name="kiekis" min="0" step="any" required></td>
                    <td><input type="number" class="form-control" id="editprekekaina" name="kaina" min="0" step="any" required></td>
                    <td><select class="form-select" id="editpreketipasid" name="tipas_id" required>
                        <option selected></option>
                        <%  for (Tipas tipas : tipai) { %>
                        <option value="<%=tipas.getId()%>"><%=tipas.getPavadinimas()%></option>
                        <% } %>
                        </select>
                    </td>

                  </tr>
                </tbody>
            </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" src="img/close.png" onclick="closeEditPrekeModalF()"  alt="Close" width="40" height="42">
          </button>
          <button type="submit" class="btn btn-primary">
            <img class="imgBtn" src="img/ok.png"  alt="Ok" width="40" height="42">
          </button>
         </form>
        </div>
      </div>
    </div>
  </div>

<!---------------------------------- Modal Delete preke -->
<div class="modal fade" id="deletePrekeModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="deletePrekeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-xl">
     <div class="modal-content border border-primary">
        <div class="modal-header bg-info">
          <h5 class="modal-title" id="editCekisModalLabel">I??TRINTI PREK??</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" >
            <img class="imgBtn" onclick="closeDeletePrekeModalF()" src="img/close.png" alt="Close" width="25" height="25">
          </button>
        </div>
        <div class="modal-body">
          <form action="deletePreke" method="POST">
             <input type="hidden" id="deleteprekeid" name="id">
            <table class="table border border-primary border-2">
                <thead>
                  <tr class="table-primary rounded-top">
                    <th scope="col"><img src="img/hamper.png" alt="hamper" width="40" height="42">&nbsp Prek??</th>
                    <th scope="col"><img src="img/numbers.png" alt="numbers" width="40" height="42">&nbsp Kiekis</th>
                    <th scope="col"><img src="img/cash.png" alt="cash"width="40" height="42">&nbsp Kaina </th>
                    <th scope="col"><img src="img/type.png" alt="type"width="40" height="42">&nbsp Tipas </th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td id="deleteprekepreke" name="preke"></td>
                    <td id="deleteprekekiekis" name="kiekis"></td>
                    <td id="deleteprekekaina" name="kaina"></td>
                    <td id="deletepreketipasid" name="tipasid"></td>
                  </tr>
                </tbody>
            </table>

        </div>
        <div class="modal-footer">
          <button type="button" class="btn" data-bs-dismiss="modal" aria-label="Close">
            <img class="imgBtn" src="img/close.png" onclick="closeDeletePrekeModalF()"  alt="Close" width="40" height="42">
          </button>
          <button type="submit" class="btn btn-primary">
            <img class="imgBtn" src="img/ok.png"  alt="Ok" width="40" height="42">
          </button>
         </form>
        </div>
      </div>
    </div>
  </div>
</div>

<!---------------------------------- Ataskaitos ---------------------------------->
<!---------------------------- ATASKAITA1 -->
<div class="modal fade" id="ataskaita1Modal" data-bs-backdrop="static" tabindex="-1" aria-labelledby="ataskaita1ModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content border border-primary">
         <div class="modal-header bg-info">
          <h5 class="modal-title" id="ataskaita1ModalLabel">ATASKAITA - BENDRA PIRKINI?? SUMA U?? PERIOD??</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
         <table class="table border border-primary border-2">
           <thead>
               <tr class="table-primary rounded-top">
                  <th scope="col"><img src="img/date-from.png" alt="date"width="40" height="42">&nbsp Data nuo</th>
                  <th scope="col"><img src="img/date-to.png" alt="date"width="40" height="42">&nbsp Data iki</th>
               </tr>
           </thead>
           <tbody>
               <tr>
                  <td><input type="date" class="form-control" name="data_nuo" id="ataskaita1datanuo" required></td>
                  <td><input type="date" class="form-control" name="data_iki" id="ataskaita1dataiki" required></td>
               </tr>
           </tbody>
         </table>
          <div id="atskaita1DIV">
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn " data-bs-dismiss="modal">
            <img class="imgBtn" src="img/close.png"  alt="Close" width="40" height="42">
        </button>
         <button type="button" class="btn btn-primary">
            <img class="imgBtn" src="img/report.png" onclick="ataskaita1ModalF()" alt="Report" width="40" height="42">
        </button>
      </div>
    </div>
  </div>
</div>

<!---------------------------- ATASKAITA2 -->
<div class="modal fade" id="ataskaita2Modal"  data-bs-backdrop="static"  tabindex="-1" aria-labelledby="ataskaita2ModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-xl">
      <div class="modal-content border border-primary">
         <div class="modal-header bg-info">
          <h5 class="modal-title" id="ataskaita1ModalLabel">ATASKAITA - PIRKINIAI PAGAL TIP?? U?? PERIOD??</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
         <table class="table border border-primary border-2">
           <thead>
               <tr class="table-primary rounded-top">
                  <th scope="col"><img src="img/date-from.png" alt="date"width="40" height="42">&nbsp Data nuo</th>
                  <th scope="col"><img src="img/date-to.png" alt="date"width="40" height="42">&nbsp Data iki</th>
               </tr>
           </thead>
           <tbody>
               <tr>
                  <td><input type="date" class="form-control" name="data_nuo" id="ataskaita2datanuo" required></td>
                  <td><input type="date" class="form-control" name="data_iki" id="ataskaita2dataiki" required></td>
               </tr>
           </tbody>
         </table>
         <div>
           <canvas id="myChart"></canvas>
         </div>
         <div id="ataskaita2VisaSuma"></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn " data-bs-dismiss="modal">
            <img class="imgBtn" src="img/close.png" alt="Close" width="40" height="42">
        </button>
         <button type="button" class="btn btn-primary">
            <img class="imgBtn" src="img/business-report.png" onclick="ataskaita2ModalF()" alt="Report" width="40" height="42">
        </button>
      </div>
    </div>
  </div>
</div>

<!---------------------------------------- FILTRAS --->
<div class="modal fade" id="filtrasModal" tabindex="-1" aria-labelledby="filtrasModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-lg">
      <div class="modal-content border border-primary">
         <div class="modal-header bg-info">
          <h5 class="modal-title" id="filtrasModalLabel">FILTRAS</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
       <form action="index.jsp" method="POST">
         <table class="table border border-primary border-2">
           <thead>
               <tr class="table-primary rounded-top">
                  <th scope="col"><img src="img/date-from.png" alt="date"width="40" height="42">&nbsp Data nuo</th>
                  <th scope="col"><img src="img/date-to.png" alt="date"width="40" height="42">&nbsp Data iki</th>
               </tr>
           </thead>
           <tbody>
               <tr>
                  <td><input type="date" class="form-control" name="data_nuo" required></td>
                  <td><input type="date" class="form-control" name="data_iki" required></td>
               </tr>
           </tbody>
         </table>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn " data-bs-dismiss="modal">
            <img class="imgBtn" src="img/close.png" onclick="closeFiltrasModalF()"  alt="Close" width="40" height="42">
        </button>
         <button type="submit" class="btn btn-primary">
            <img class="imgBtn" src="img/ok.png"  alt="Ok" width="40" height="42">
        </button>
        </form>
      </div>
    </div>
  </div>
</div>


</body>
</html>