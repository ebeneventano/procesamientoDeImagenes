package ar.edu.untref.imagenes.tps.domain;

public enum Operations {
	
	ADD {
		public int apply(int a, int b) {
			return a + b;
		}
	},
	SUBTRACT {
		public int apply(int a, int b) {
			return a - b;
		}
	},
	MULTIPLY {
		public int apply(int a, int b) {
			return a * b;
		}
	},
	;

	public abstract int apply(int a, int b);
}