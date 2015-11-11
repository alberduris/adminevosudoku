package packModelo;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;

public class ListaOrdenadaGenerica<E>
{
    private Vector<E>     listaElementos;

    private Comparator<E> comp;

    public ListaOrdenadaGenerica(Comparator<E> pComp)
    {
	comp = pComp;
	listaElementos = new Vector<E>();
    }

    public Iterator<E> obtIterador()
    {
	return listaElementos.iterator();
    }

    public void anadirElemento(E pElem)
    {
	int pos = 0;
	if (listaElementos.isEmpty())
	{
	    listaElementos.add(pElem);
	} else
	{
	    while (pos < listaElementos.size()
		    && comp.compare(listaElementos.get(pos), pElem) > 0)
	    {
		pos++;
	    }
	    listaElementos.add(pos, pElem);
	}
    }

    public boolean existeElementoSatisfaceCondicion(ICondicion<E> pCond)
    {
	boolean encontrado = false;
	Iterator<E> it = obtIterador();
	while (!encontrado && it.hasNext())
	{
	    if (pCond.satisfaceCondicion(it.next()))
	    {
		encontrado = true;
	    }
	}
	return encontrado;
    }

    public Iterator<E> obtIterador(ICondicion<E> pCond)
    {
	return new IteradorCondicion(pCond);
    }

    private int buscarPosElemento(ICondicion<E> pCond)
    {
	int pos = 0;
	boolean encontrado = false;
	Iterator<E> it = obtIterador();
	while (!encontrado && it.hasNext())
	{
	    if (pCond.satisfaceCondicion(it.next()))
	    {
		encontrado = true;
	    } else
	    {
		pos++;
	    }
	}
	return pos;
    }

    private class IteradorCondicion implements Iterator<E>
    {
	private int posActual;

	public IteradorCondicion(ICondicion pCond)
	{
	    posActual = buscarPosElemento(pCond);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext()
	{
	    return posActual < listaElementos.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public E next()
	{
	    if (hasNext())
	    {
		return listaElementos.elementAt(posActual++);
	    } else
	    {
		throw new NoSuchElementException();
	    }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove()
	{
	}

    }
}
