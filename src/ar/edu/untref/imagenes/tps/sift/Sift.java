package ar.edu.untref.imagenes.tps.sift;

import java.io.File;

import javax.swing.JOptionPane;

import org.openimaj.feature.local.list.LocalFeatureList;
import org.openimaj.feature.local.matcher.BasicMatcher;
import org.openimaj.feature.local.matcher.FastBasicKeypointMatcher;
import org.openimaj.feature.local.matcher.LocalFeatureMatcher;
import org.openimaj.feature.local.matcher.MatchingUtilities;
import org.openimaj.feature.local.matcher.consistent.ConsistentLocalFeatureMatcher2d;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.colour.RGBColour;
import org.openimaj.image.feature.local.engine.DoGSIFTEngine;
import org.openimaj.image.feature.local.keypoints.Keypoint;
import org.openimaj.math.geometry.transforms.estimation.RobustAffineTransformEstimator;
import org.openimaj.math.model.fit.RANSAC;

public class Sift {
	
	public static void aplicar(File imagen1, File imagen2, boolean esRobusto) throws Exception {
		MBFImage query = ImageUtilities.readMBF(imagen1);
		MBFImage target = ImageUtilities.readMBF(imagen2);
	
		// Se crea un motor de diferencia guassiana, bastante resistente a
		// cambios de tamaño, rotaciones y reposicionamiento
		DoGSIFTEngine engine = new DoGSIFTEngine();
	
		// Se extraen los Keypoints de cada imagen.
		LocalFeatureList<Keypoint> queryKeypoints = engine.findFeatures(query.flatten());
		LocalFeatureList<Keypoint> targetKeypoints = engine.findFeatures(target.flatten());
	
		LocalFeatureMatcher<Keypoint> matcher = new BasicMatcher<Keypoint>(80);
		matcher.setModelFeatures(queryKeypoints);
		matcher.findMatches(targetKeypoints);
		
		if (esRobusto){
			RobustAffineTransformEstimator modelFitter = new RobustAffineTransformEstimator(
					5.0, 1500, new RANSAC.PercentageInliersStoppingCondition(0.5));
			matcher = new ConsistentLocalFeatureMatcher2d<Keypoint>(
					new FastBasicKeypointMatcher<Keypoint>(8), modelFitter);
			
			matcher.setModelFeatures(queryKeypoints);
			matcher.findMatches(targetKeypoints);
		}
	
		MBFImage consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(),
				RGBColour.RED);
		
		JOptionPane.showMessageDialog(null, " Coincidencias entre descriptores: " + 
					String.valueOf(matcher.getMatches().size()));
		
		DisplayUtilities.display(consistentMatches);
	}

}