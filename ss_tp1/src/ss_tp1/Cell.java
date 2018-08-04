package ss_tp1;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

public class Cell {
	private int row;
	private int col;
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public List<Cell> getNeighbours(int size) {
		List<Cell> ret = new ArrayList<>();
		if(row > 0) {
			ret.add(new Cell(row - 1, col));
		}
		if(row > 0 && col < size-1) {
			ret.add(new Cell(row - 1, col + 1));
		}
		if(col < size-1) {
			ret.add(new Cell(row, col + 1));
		}
		if(row < size - 1 && col < size - 1) {
			ret.add(new Cell(row + 1, col + 1));
		}
		return ret;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
