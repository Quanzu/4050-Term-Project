package edu.uga.cs.evote.persistence.impl;

import edu.uga.cs.evote.persistence.Persistable;
import edu.uga.cs.evote.persistence.PersistenceLayer;

public abstract class Persistent implements Persistable {

	private long id;
	private static PersistenceLayer persistenceLayer;
	
	public Persistent() {
        this.id = -1;
    }
    
    public Persistent( long id ) {
        this.id = id;
    }
	
	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public boolean isPersistent() {
		return id >= 0;
	}
	
	public static PersistenceLayer getPersistencaLayer() {
        return persistenceLayer;
    }

    public static void setPersistencaLayer(PersistenceLayer pLayer) {
        persistenceLayer = pLayer;
    }

}
