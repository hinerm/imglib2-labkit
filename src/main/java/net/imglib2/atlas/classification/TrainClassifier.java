package net.imglib2.atlas.classification;

import java.awt.event.ActionEvent;
import java.util.function.Supplier;

import net.imglib2.atlas.MainFrame;
import net.imglib2.atlas.labeling.Labeling;
import org.scijava.ui.behaviour.util.AbstractNamedAction;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;

public class TrainClassifier extends AbstractNamedAction
{

	private Supplier<Labeling> labelingSupplier;

	public TrainClassifier(
			final MainFrame.Extensible extensible,
			final Classifier classifier,
			final Supplier<Labeling> labelingSupplier,
			final RandomAccessibleInterval<?> image
	)
	{
		super( "Train Classifier" );
		this.classifier = classifier;
		this.labelingSupplier = labelingSupplier;
		this.image = image;
		extensible.addAction(this, "ctrl shift T");
	}

	private final Classifier classifier;

	private final RandomAccessibleInterval<?> image;

	private boolean trainingSuccess = false;

	public boolean getTrainingSuccess()
	{
		return trainingSuccess;
	}

	@Override
	public void actionPerformed( final ActionEvent e )
	{
		trainingSuccess = false;
		try
		{
			classifier.train( image, labelingSupplier.get());
			trainingSuccess = true;
		}
		catch ( final Exception e1 )
		{
			System.out.println("Training was interrupted by exception:");
			e1.printStackTrace();
			trainingSuccess = false;
		}
	}

}
