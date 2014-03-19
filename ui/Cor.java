/* Gil Barbosa Reis - 8532248
 * SCC 604 - POO - Turma C
 * 17/03/2014
 */
package ui;

public enum Cor {
	BRANCO,
	PRETO;
	
	public boolean ehCorOposta (Cor cmp) {
		return (this == cmp);
	}
}
