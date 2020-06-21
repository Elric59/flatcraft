# Fonctionnement du glisser/déplacer

## Principe

Le glisser/déplacer est une fonctionnalité offerte par tous les composants Swing de Java.

Il est basé sur l'échange d'objets `Transferable` entre une source et une destination.
Le type de ces objets conditionne la possibilité d'effectuer le glisser/déplacer.

Le programmeur doit simplement associer définir quelles sont les données transférables
et ensuite comment réagir en cas de glisser/déplacer, en associant un `TransfertHandler`
particulier au composant Swing.

## Les données à transférer

Il suffit d'implémenter l'interface `Transferable`. 

```java
class MyData implements Transferable {

    public static final DataFlavor MY_FLAVOR = new DataFlavor(My.class, "myflavor");

    private String unMessage;
    
    public MyData(String unMessage) {
         this.unMessage = unMessage;
    }
    
    public String getMessage() {
         return unMessage;
    }
    
    public void setMessage(String str) {
         this.unMessage = str;
    }
    
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] {  MY_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return  MY_FLAVOR.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return this;
	}
}
```



## La source

```java
class SourceTH extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getSourceActions(JComponent c) {
	    return MOVE | COPY;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
	    return new MyData("Bad example");
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
	    MyData mydata = ((MyData) data);
	    if (action == MOVE) {
	       // quand on déplace
	    } else {
	       // quand on copie
	    }
	}
}
```

## La cible

```java
class CibleTH extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean canImport(TransferSupport support) {
	    return support.isDataFlavorSupported(MY_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
	    System.err.println("Importing data");
	    if (support.isDrop()) {
	        // transtypage vers l'objet source (connu du programmeur)
	        JPanel source = (JPanel) support.getComponent();
	        try {
	            MyData data = (MyData) support.getTransferable()
	                    .getTransferData(MY_FLAVOR);
	           System.err.println(data.getMessage());
	            if (support.getDropAction() == MOVE) { 
	               System.err.println("Moving data");
	               data.setMessage("");
	            } else {
	                System.err.println("Copying data");
	            }
	            return true;
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	            return false;
	        }
	    } else {
	        return false;
	    }
	}
}
```


## Détection de l'action "Drag&Drop" avec la souris

Il est nécessaire d'associer un écouteur particulier au composant source pour déclencher l'évènement de glisser/déplacer.


```java 
        MouseListener mouselistener = new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JComponent comp = (JComponent) me.getSource();
				TransferHandler handler = comp.getTransferHandler();
				if (me.getButton() == MouseEvent.BUTTON1) {
					handler.exportAsDrag(comp, me, TransferHandler.MOVE);
				} else {
					handler.exportAsDrag(comp, me, TransferHandler.COPY);
				}
			}
		};
```