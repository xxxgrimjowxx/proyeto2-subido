import sumaApp.*;
//importar la interfaz idl
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;

class SumaImpl extends sumaPOA {
	private ORB orb;
	//crear ina instancia de orb
	public void setORB(ORB orb_val) {
		orb = orb_val;

	}
	//setear el orb para generar las conecciones

	public int sumar(int primernumero, int segundonumero) {
		return primernumero + segundonumero;
	}
	//metodo q va a enviar

	public void shutdown() {
		orb.shutdown(false);
		//cerrar orb por parte del  servidor
	}
}

public class SumaServer {

	public static void main(String args[]) {
		try {
			ORB orb = ORB.init(args, null);
			//inicializacion de conexion
			POA rootpoa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			//abrimos el root del POA
			rootpoa.the_POAManager().activate();

			SumaImpl sumaImpl = new SumaImpl();
			//implementamos referencia
			sumaImpl.setORB(orb);
			//seteamos ORB

			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(sumaImpl);
			suma href = sumaHelper.narrow(ref);
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			//le damos nombre al servicio
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			String name = "Suma";
			//se envia el nombre para que resiva la interface
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);
			System.out.println("Servidor de suma listo y en espera");
			//confirmacion de servidor conectado
			orb.run();
		} catch (Exception e) {
			System.err.println("Error: " + e);
			e.printStackTrace(System.out);
		}

		System.out.println("Adios cerrando servidor");
	}
}