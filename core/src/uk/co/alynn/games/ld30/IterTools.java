package uk.co.alynn.games.ld30;

import java.util.Iterator;

public abstract class IterTools {
    @SuppressWarnings("rawtypes")
    public static Iterator zero() {
        return new Iterator() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Object next() {
                return null;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    @SuppressWarnings("rawtypes")
    public static Iterator just(final Object a) {
        return new Iterator() {
            boolean collected = false;
            
            @Override
            public boolean hasNext() {
                return !collected;
            }
            
            @Override
            public Object next() {
                if (collected) {
                    return null;
                } else {
                    collected = true;
                    return a;
                }
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
