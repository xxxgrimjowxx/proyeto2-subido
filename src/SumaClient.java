import java.util.Scanner;
import sumaApp.*;
//importar la interfaz idl
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

public class SumaClient {
	static suma sumaImpl;

	public static void main(String args[]) {
		Scanner sc = new Scanner (System.in);

		try {
			ORB orb = ORB.init(args, null);
			//inicializar orb para enviar peticion
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			String name = "Suma";
			sumaImpl = sumaHelper.narrow(ncRef.resolve_str(name));
			// System.out.println("Obteniendo las cabeceras del objeto servidor:
			// "+sumaImpl);
			System.out.println("ingrese a");
			int a = sc.nextInt();
			System.out.println("ingrese b");
			int b = sc.nextInt();
			System.out.println("suma es:"
					+ Integer.toString(sumaImpl.sumar(a, b)));
			sumaImpl.shutdown();
		} catch (Exception e) {
			System.out.println("Error: " + e);
			e.printStackTrace(System.out);
		}
	}
}