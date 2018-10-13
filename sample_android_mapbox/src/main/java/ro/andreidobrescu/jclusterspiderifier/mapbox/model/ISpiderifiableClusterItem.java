package ro.andreidobrescu.jclusterspiderifier.mapbox.model;

import com.mapbox.mapboxsdk.plugins.cluster.clustering.ClusterItem;

import ro.andreidobrescu.jclusterspiderifier.ISpiderifiablePin;

public interface ISpiderifiableClusterItem extends ClusterItem, ISpiderifiablePin
{
}
