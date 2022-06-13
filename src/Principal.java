
import POJOS.Departamento;
import POJOS.Empleado;
import POJOS.Retenciones;
import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alumno
 */
public class Principal {
    
    public static void main(String[] args) {
        
        SessionFactory sf = HibernateUtil.sessionFactory();
        Session sesion = sf.openSession();
        Transaction t = sesion.beginTransaction();
        
        
        Query qSelectEmpleado = sesion.createQuery("From Empleado");
        Query qSelectRetenciones = sesion.createQuery("From Retenciones");
        Departamento dept=null;
        Retenciones ret =null;
        List<Empleado> lista = qSelectEmpleado.list();
        
        int retencion=0;
        int salario=0;
        
        for (int i = 0; i <lista.size(); i++) {
            
            if(lista.get(i).getSalario()<1500){
                retencion = lista.get(i).getSalario()*10/100;
                
            }else if(lista.get(i).getSalario()<2500){
                retencion = lista.get(i).getSalario()*15/100;
            }else retencion = lista.get(i).getSalario()*20/100;
            
            salario = lista.get(i).getSalario()-retencion;
            
            ret = new Retenciones(lista.get(i).getNombre(), lista.get(i).getDepartamento().getNombre(), lista.get(i).getSalario(), salario, retencion);
      sesion.save(ret);
        }
        
        List<Retenciones> listaRet = qSelectRetenciones.list();
        listaRet= qSelectRetenciones.list();
        
        System.out.println("TABLA RETENCIONES ANTES DE ACTUALIZARSE");
        for (int i = 0; i <listaRet.size(); i++) {
            System.out.println(listaRet.get(i).toString());
            
            System.out.println("-----------------------------------------------------------------");
        int salarioNeto =0;
            for (int j = 0; j <listaRet.size(); j++) {
                
                if(listaRet.get(i).getSalarioNeto()<750)
                    salarioNeto=1000;
                else if(listaRet.get(i).getSalarioNeto()<2000)
                    salarioNeto=2300;
                else if(listaRet.get(i).getSalarioNeto()<3200)
                    salarioNeto=3300;
                listaRet.get(i).setSalarioNeto(salarioNeto);
                sesion.update(listaRet.get(i));
                
                
            }
                
        
            System.out.println("TABLA RETENCIONES DESPUES DE ACTUALIZAR");
            
            listaRet = qSelectRetenciones.list();
            for (int j = 0; j <listaRet.size(); j++) {
                
                System.out.println(listaRet.get(i).toString());
            }
            
            
            
        
        }
        
        
        
        
        
        
        
        
        t.commit();
        sesion.close();
        System.exit(0);
        
        
        
        
    }
}
