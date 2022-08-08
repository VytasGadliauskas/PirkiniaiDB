 let tableRowElement;
    function addCekisModalF() {
        document.body.classList.add("modal-open");
        document.getElementById("addCekisModal").style.display = "block"
        document.getElementById("addCekisModal").classList.add("show");
    }

    function closeAddCekisModalF() {
        document.body.classList.remove("modal-open");
        document.getElementById("addCekisModal").style.display = "none"
    }

    function editCekisModalF(element,cekis_id) {
        tableRowElement = element.parentElement.parentElement;
        const id = tableRowElement.getElementsByClassName('tablecekisid')[0].innerHTML;
        const data = tableRowElement.getElementsByClassName('tablecekisdata')[0].innerHTML;
        const parduotuve = tableRowElement.getElementsByClassName('tablecekisparduotuve')[0].innerHTML;
        const aprasymas = tableRowElement.getElementsByClassName('tablecekisaprasymas')[0].innerHTML;

        document.getElementById('editcekisid').value = id;
        document.getElementById('editcekisdata').value = data;
        document.getElementById('editcekisparduotuve').value = parduotuve;
        document.getElementById('editcekisaprasymas').value = aprasymas;
        loadPrekes(cekis_id);
        document.getElementById("editCekisModal").style.display = "block"
        document.getElementById("editCekisModal").classList.add("show");
    }

    function closeEditCekisModalF() {
        document.getElementById("editCekisModal").style.display = "none"
    }

    function deleteCekisModalF(element) {
        tableRowElement = element.parentElement.parentElement;
        const id = tableRowElement.getElementsByClassName('tablecekisid')[0].innerHTML;
        const data = tableRowElement.getElementsByClassName('tablecekisdata')[0].innerHTML;
        const parduotuve = tableRowElement.getElementsByClassName('tablecekisparduotuve')[0].innerHTML;
        const aprasymas = tableRowElement.getElementsByClassName('tablecekisaprasymas')[0].innerHTML;

        document.getElementById('deletecekisid').value = id;
        document.getElementById('deletecekisdata').innerHTML = data;
        document.getElementById('deletecekisparduotuve').innerHTML = parduotuve;
        document.getElementById('deletecekisaprasymas').innerHTML = aprasymas;

        document.getElementById("deleteCekisModal").style.display = "block"
        document.getElementById("deleteCekisModal").classList.add("show");
    }

   function closeDeleteCekisModalF() {
        document.getElementById("deleteCekisModal").style.display = "none";
   }
 ///////////////////////////////  PREKES
  function editPrekesModalF(element) {
        // tableRowElement = element.parentElement.parentElement.parentElement.parentElement;

        // console.log(element.parentElement.parentElement.parentElement.parentElement.parentElement);
        //  const cekis_id = tableRowElement.getElementsByClassName('cekis_id')[0].value;
        //  const data = tableRowElement.getElementsByClassName('tablecekisdata')[0].innerHTML;
        //  const parduotuve = tableRowElement.getElementsByClassName('tablecekisparduotuve')[0].innerHTML;
        //  const aprasymas = tableRowElement.getElementsByClassName('tablecekisaprasymas')[0].innerHTML;

        //  document.getElementById('editcekisid').value = id;
        //  document.getElementById('editcekisdata').value = data;
        //  document.getElementById('editcekisparduotuve').value = parduotuve;
        //  document.getElementById('editcekisaprasymas').value = aprasymas;
        //  loadPrekes(cekis_id);
          document.getElementById("editPrekesModal").style.display = "block"
          document.getElementById("editPrekesModal").classList.add("show");
      }

      function closeEditPrekesModalF() {
          document.getElementById("editPrekesModal").style.display = "none"
          document.getElementById("editCekisModal").style.display = "block"
          document.getElementById("editCekisModal").classList.add("show");
      }

   function addPrekeModalF(element) {

          document.getElementById("addPrekeModal").style.display = "block"
          document.getElementById("addPrekeModal").classList.add("show");
      }

      function closeAddPrekeModalF() {
          document.getElementById("addPrekeModal").style.display = "none"
                    document.getElementById("editPrekesModal").style.display = "block"
                    document.getElementById("editPrekesModal").classList.add("show");
      }

  function editPrekeModalF(element) {
          tableRowElement = element.parentElement.parentElement;
          console.log(tableRowElement);

          const id = tableRowElement.getElementsByClassName('tableprekeid')[0].innerHTML;
          const preke = tableRowElement.getElementsByClassName('tableprekepreke')[0].innerHTML;
          const kiekis = tableRowElement.getElementsByClassName('tableprekekiekis')[0].innerHTML;
          const kaina = tableRowElement.getElementsByClassName('tableprekekaina')[0].innerHTML;
          const tipas_id = tableRowElement.getElementsByClassName('tablepreketipasid')[0].innerHTML;

          document.getElementById('editprekeid').value = id;
          document.getElementById('editprekepreke').value = preke;
          document.getElementById('editprekekiekis').value = kiekis;
          document.getElementById('editprekekaina').value = kaina;
          document.getElementById('editpreketipasid').value = tipas_id;

          document.getElementById("editPrekeModal").style.display = "block"
          document.getElementById("editPrekeModal").classList.add("show");
      }

      function closeEditPrekeModalF() {
          document.getElementById("editPrekeModal").style.display = "none"
          document.getElementById("editPrekesModal").style.display = "block"
          document.getElementById("editPrekesModal").classList.add("show");
      }

 function deletePrekeModalF(element) {
        tableRowElement = element.parentElement.parentElement;
        console.log(tableRowElement);

        const id = tableRowElement.getElementsByClassName('tableprekeid')[0].innerHTML;
        const preke = tableRowElement.getElementsByClassName('tableprekepreke')[0].innerHTML;
        const kiekis = tableRowElement.getElementsByClassName('tableprekekiekis')[0].innerHTML;
        const kaina = tableRowElement.getElementsByClassName('tableprekekaina')[0].innerHTML;
        const tipas_id = tableRowElement.getElementsByClassName('tablepreketipasid')[0].innerHTML;

        document.getElementById('deleteprekeid').value = id;
        document.getElementById('deleteprekepreke').innerHTML = preke;
        document.getElementById('deleteprekekiekis').innerHTML = kiekis;
        document.getElementById('deleteprekekaina').innerHTML = kaina;
        document.getElementById('deletepreketipasid').innerHTML = tipas_id;

        document.getElementById("deletePrekeModal").style.display = "block"
        document.getElementById("deletePrekeModal").classList.add("show");
    }

   function closeDeletePrekeModalF() {
        document.getElementById("deletePrekeModal").style.display = "none";
   }

   /////////////////////////////////// ATASKAITOS

   function ataskaita1ModalF() {
           const data_nuo = document.getElementById("ataskaita1datanuo").value;
           const data_iki = document.getElementById("ataskaita1dataiki").value;
           ataskaita1F(data_nuo, data_iki)
   }

   function ataskaita2ModalF() {
           const data_nuo = document.getElementById("ataskaita2datanuo").value;
           const data_iki = document.getElementById("ataskaita2dataiki").value;
           ataskaita2F(data_nuo, data_iki)
   }

  ///////////////////////////////////  AJAX

  function loadPrekes(cekis_id) {
    const xhttp = new XMLHttpRequest();
    document.getElementById('addprekecekisid').value = cekis_id;

    xhttp.onload = function() {
      document.getElementById("prekesList").innerHTML =
      this.responseText;
    }
    xhttp.open("GET", "listPrekes?cekis_id="+cekis_id);
    xhttp.send();
  }

  function ataskaita1F(data_nuo, data_iki) {
      const xhttp = new XMLHttpRequest();
      xhttp.onload = function() {
          document.getElementById("atskaita1DIV").innerHTML = this.responseText;
      }
      xhttp.open("GET", "ataskaita1?data_nuo="+data_nuo+"&data_iki="+data_iki);
      xhttp.send();
    }

  /////////////////////// ATASKAITA2 AJAX JCHART

function ataskaita2F(data_nuo, data_iki){

       const xhttp = new XMLHttpRequest();
          xhttp.onload = function() {
              const raport2JSON = JSON.parse(this.responseText);
         // console.log(raport2JSON);
              const raportObj = JSON.parse(JSON.stringify(raport2JSON));
         // console.log(raportObj);
               const data = {
                      labels: Object.keys(raportObj),
                      datasets: [{
                        label: 'suma Eu: ',
                        backgroundColor: 'rgb(255, 99, 132)',
                        borderColor: 'rgb(255, 99, 132)',
                        data: Object.values(raportObj),
                      }]
                    };
                const config = {
                       type: 'bar',
                       data: data,
                       options: {}
                     };

                let chartStatus = Chart.getChart("myChart");
                if (chartStatus != undefined) {
                  chartStatus.destroy();
                }
                const myChart = new Chart(document.getElementById('myChart'),config);

                document.getElementById('ataskaita2VisaSuma').innerHTML = "Bendra suma : "+Object.values(raportObj).reduce((a, b) => a + b, 0)+" Eu";
          }
          xhttp.open("GET", "ataskaita2?data_nuo="+data_nuo+"&data_iki="+data_iki);
          xhttp.send();
 }
