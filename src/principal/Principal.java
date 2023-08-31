
package principal;

import java.util.Collection;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;


public class Principal {

    public static void main(String[] args) {
        try{
            
            //creando las Entidad Factory
            EntityManagerFactory emf  = Persistence.createEntityManagerFactory("TiendaJPAPU");
            EntityManager em = emf.createEntityManager();
            
            EntityManager em1 = Persistence.createEntityManagerFactory("TiendaJPAPU").createEntityManager();
            /*
            try{
                //creando 02 registros de fabricante
                Fabricante fb1 = new Fabricante();
                fb1.setId(UUID.randomUUID().toString());
                fb1.setCodigo(1);
                fb1.setNombre("Asus");
                
                em.getTransaction().begin();
                em.persist(fb1);
                em.getTransaction().commit();
                
                Fabricante fb2 = new Fabricante();
                fb2.setId(UUID.randomUUID().toString());
                fb2.setCodigo(2);
                fb2.setNombre("SAMSUNG");
                
                em.getTransaction().begin();
                em.persist(fb2);
                em.getTransaction().commit();
                
                //creando 01 registro de producto
                Producto prod1 = new Producto();
                prod1.setId(UUID.randomUUID().toString());
                prod1.setCodigo(100);
                prod1.setNombre("PC Portatil 1");
                prod1.setPrecio(500);
                prod1.setFabricante(fb1);
                
                em.getTransaction().begin();
                em.persist(prod1);
                em.getTransaction().commit();
                
            }catch(Exception e){
                throw e;
            }
            */
            
            //realizando consultas
            //buscando fabricante por criterio, solo un registro getSingleResult
            int codigo = 1;
            Fabricante fbr1 = (Fabricante) em.createQuery(
                    "SELECT f FROM Fabricante f WHERE f.codigo =:codigo").
                    setParameter("codigo", codigo).
                    getSingleResult();
            System.out.println("Busqueda por Criterio");
            System.out.println(fbr1.toString().toString());
            
            System.out.println("");
            
            //Buscando por id
            Fabricante fbr2 = em.find(Fabricante.class, fbr1.getId());            
            System.out.println("Busqueda por Id");
            System.out.println(fbr2.toString());
            
            System.out.println("");
            
            //Lista de fabricantes se usa getResultList
            Collection<Fabricante> fabricantes = em.createQuery(" SELECT f "
                +"FROM Fabricante f").getResultList();
            System.out.println("Lista de Fabricantes ");
            for(Fabricante fb : fabricantes){
                System.out.println(fb.toString());
            }
            
            System.out.println("");
            
            //Buscado un producto
            Producto prod1 = (Producto) em.createQuery("SELECT p FROM Producto p WHERE"
                    + " p.codigo=:codigo").setParameter("codigo", 100).
                    getSingleResult();
            
            System.out.println("Busqueda de Producto por criterio: codigo");
            System.out.println(prod1.toString());
            
            System.out.println("");
            
            System.out.println("Buscando un producto inexistente");
            
            try{
                
                Producto prod2 = (Producto) em.createQuery("SELECT p FROM Producto p WHERE"
                    + " p.codigo=:codigo").setParameter("codigo", 100).
                    getSingleResult();
                
            }catch(NoResultException e){
                System.out.println("No se encontro el producto para el codigo indicado");
            }
            
            //buscando un Fabricante por criterio y modificar el nombre
            try{
                //busco por criterio
                int codigos = 1;
                Fabricante fabricante = (Fabricante) em.createQuery("SELECT f FROM Fabricante f "
                        +"WHERE f.codigo = :codigos").setParameter("codigos", codigos).
                        getSingleResult();
                
                //imprimiendo el resultado
                System.out.println("Fabricante encontrado:");
                System.out.println(fabricante.toString());
                
                System.out.println("");
                fabricante.setNombre("Lenovo");
                em.getTransaction().begin();
                em.merge(fabricante);
                em.getTransaction().commit();
                
                fabricante = (Fabricante) em.createQuery("SELECT f FROM Fabricante f "
                        +"WHERE f.codigo = :codigos").setParameter("codigos", codigos).
                        getSingleResult();
                
                System.out.println("Fabricante actualizado:");
                System.out.println(fabricante.toString());
            }catch(Exception e){
                throw e;
            }  
            
            
        }catch(Exception ex){
            throw ex;
        }
               
    }
    
      
    
}
