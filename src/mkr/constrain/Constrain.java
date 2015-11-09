package mkr.constrain;

import mkr.StiffnessLineCreator;

public abstract class Constrain implements StiffnessLineCreator{
    protected int index;
    protected double value;

    public Constrain(int index , double value){
        this.index = index;
        this.value = value;
    }

    public int getNumberNode(){
        return index;
    }

    @Override
    public boolean equals(Object object){
        if(!(object instanceof Constrain))
            return false;
        Constrain constrain = (Constrain)object;
        return constrain.getNumberNode() == index;
    }

    @Override
    public int hashCode(){
        return index;
    }
}
