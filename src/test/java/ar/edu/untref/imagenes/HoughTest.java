package ar.edu.untref.imagenes;

import org.junit.Assert;
import org.junit.Test;

import ar.edu.untref.imagenes.tps.domain.MatrizAcumuladora;
import ar.edu.untref.imagenes.tps.hough.Parametro;

@SuppressWarnings("javadoc")
public class HoughTest {

	@Test
	public void seGeneraUnEspacioDeParametrosDe2LineasVerticalesY2LineasHorizontalesParaLaImagenDelCuadrado() {

		int rhoMinimo = 0;
		int rhoMaximo= 200; // Diagonal de la imágen
		
		int tethaMinimo = 0;
		int tethaMaximo = 90;
		
		int discretizacionesRho = 4; // Me interesa la 0, 50, 100 y 150
		int discretizacionesTetha = 2; // Me interesa 0 y 90 grados
			
		
		MatrizAcumuladora matriz = new MatrizAcumuladora(rhoMinimo, rhoMaximo,
															tethaMinimo, tethaMaximo,
															discretizacionesRho, discretizacionesTetha);
		
		Parametro[][] espacioDeParametros = matriz.getEspacioParametros();
		
		Assert.assertEquals("Se generaron 4 Rho", 4, espacioDeParametros.length);
		Assert.assertEquals("Se generaron 2 Tetha", 2, espacioDeParametros[0].length);

		// Recta horizontal inferior
		Assert.assertEquals(0, espacioDeParametros[0][0].getTetha().intValue());
		
		// Recta horizontal superior
		Assert.assertEquals(0, espacioDeParametros[1][0].getTetha().intValue());
		
		// Recta vertical izquierda
		Assert.assertEquals(90, espacioDeParametros[0][1].getTetha().intValue());
		
		// Recta vertical derecha
		Assert.assertEquals(90, espacioDeParametros[1][1].getTetha().intValue());
		
	}

}