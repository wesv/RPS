package util;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;

public class DropInput implements DropTargetListener {

	@Override
	public void dropActionChanged(DropTargetDragEvent arg0) {
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		event.acceptDrop(DnDConstants.ACTION_COPY);
		Transferable transferable = event.getTransferable();
		DataFlavor[] flavors = transferable.getTransferDataFlavors();
		for (DataFlavor flavor : flavors) {
			List<File> files;
			try {
				files = (List) transferable.getTransferData(flavor);

				for (File file : files) {
					DropInputDecoder.decode(file);
				}

			} catch (UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
	}

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
	}

}
