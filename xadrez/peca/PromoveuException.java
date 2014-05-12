/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 11/05/2014
 */
package xadrez.peca;

public class PromoveuException extends Exception {
	String S;
	Peca nova;
	
	public PromoveuException (Peca nova) {
		S = "promoveu, UHUL!";
		this.nova = nova;
	}
	
	public Peca getNova () {
		return nova;
	}
}
