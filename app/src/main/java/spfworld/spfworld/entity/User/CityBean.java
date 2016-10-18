package spfworld.spfworld.entity.User;

import java.util.List;

public class CityBean {

	private int i;
	private String n;
	private List<CityItem> c;

	@Override
	public String toString() {
		return "CityBean [i=" + i + ", n=" + n + ", c=" + c + "]";
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<CityItem> getC() {
		return c;
	}

	public void setC(List<CityItem> c) {
		this.c = c;
	}

	public class CityItem {
		private int i;
		private String n;
		private List<CityItemBean> d;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		public String getN() {
			return n;
		}

		public void setN(String n) {
			this.n = n;
		}

		public List<CityItemBean> getD() {
			return d;
		}

		public void setD(List<CityItemBean> d) {
			this.d = d;
		}

		@Override
		public String toString() {
			return "CityItem [i=" + i + ", n=" + n + ", d=" + d + "]";
		}

		public class CityItemBean {
			private int i;
			private String n;

			public int getI() {
				return i;
			}

			public void setI(int i) {
				this.i = i;
			}

			public String getN() {
				return n;
			}

			public void setN(String n) {
				this.n = n;
			}

			@Override
			public String toString() {
				return "CityItemBean [i=" + i + ", n=" + n + "]";
			}

		}
	}
}
