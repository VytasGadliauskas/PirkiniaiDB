package lt.bit.prekes;

import jakarta.persistence.*;
import lt.bit.prekes.data.Cekis;

import java.util.List;

public class Testams {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("prekes_pu");
        try {
            EntityManager em = emf.createEntityManager();

          //  double visaSuma = 0.0;
            // EntityTransaction tx = em.getTransaction();
            // tx.begin();
           //  Query query = em.createQuery("SELECT c FROM Cekis c");
         //  Query qvisasuma2 = em.createNativeQuery("select sum(p.kiekis*p.kaina) from Prekes p");
         //  Object visaSuma = qvisasuma2.getSingleResult();
         //  double visaSuma = Double.parseDouble(qvisasuma2.getSingleResult().toString());
          // System.out.println("===> visa suma: "+visaSuma);
          //  for (Object o : visaSumaList) {
          //    Double visaSuma = (Double) o;
//            Query qvisasumacekio = em.createNativeQuery("select sum(kiekis*kaina) as suma from Prekes WHERE `cekis_id`=:cekisid");
//            qvisasumacekio.setParameter("cekisid", 1);
//            double visaSumaCekio = Double.parseDouble(qvisasumacekio.getSingleResult().toString());
//                System.out.println("===============> "+visaSumaCekio);
//
  //          }


          //  Query q = em.createNativeQuery("select Tipas.pavadinimas as tipas, sum(Prekes.kiekis*Prekes.kaina) as suma  from Cekis, Prekes, Tipas" +
          //          " WHERE Cekis.id=Prekes.cekis_id AND Prekes.tipas_id=Tipas.id " +
          //          " GROUP BY Tipas.pavadinimas");
          //  List<Object[]> list = q.getResultList();

         //   for (Object[] o:  list) {
         //       System.out.println(o[0].toString() +","+ Double.parseDouble(o[1].toString()));
         //   }

            Query qvisasumacekio = em.createNativeQuery("select sum(kiekis*kaina) as suma from Prekes WHERE `cekis_id`=:cekisid");
            qvisasumacekio.setParameter("cekisid", 29);
            double visaSumaCekio = 0.0;
            if (qvisasumacekio.getSingleResult() != null){
                visaSumaCekio = Double.parseDouble(qvisasumacekio.getSingleResult().toString());
            }
            System.out.println(visaSumaCekio);


            em.close();

        } catch (Exception ex){
          //  response.sendRedirect("klaida.jsp");
        }
    }
}
