package utility;

import java.util.ArrayList;
import java.util.List;

public class StringToInterger {

	private int index = 0;
	private int numero = 0;
	private List<Integer> numeri = new ArrayList<Integer>();

	public List<Integer> getNumeri(String s) {
		for (; index < s.length(); index++) {
			if(containsAnumber(copia(s, index))) {
				makeNumero(s);
				numeri.add(numero);
				reset();
			}
		}
		return numeri;
	}

	private void makeNumero(String s) {
		boolean marcatore = false;
		for (int i = this.index; i < s.length(); i++) {
			try {
				this.index = i;
				numero = numero * 10 + Integer.parseInt(s.charAt(i) + "");
				marcatore = true;
			} catch (Exception e) {
				// TODO: handle exception
				if (marcatore) {
					break;
				}
			}
		}
	}
	
	
	private void reset() {
		numero = 0;
	}
	
	private String copia(String s, int i) {
		String r = "";
		for (; i < s.length(); i++) {
			r = r + s.charAt(i);
		}
		return r;
	}

	private boolean containsAnumber(String s) {
		return s.contains("0") || s.contains("1") || s.contains("2") || s.contains("3") || s.contains("4") || s.contains("5") || s.contains("6") || s.contains("7") || s.contains("8") || s.contains("9");
	}
	
}
