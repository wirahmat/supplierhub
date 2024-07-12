package com.supplierhub.supplierhub.common.constant;

public enum Reason {
	NEW, MAINTENANCE, BROKEN, NEED_REPAIR;

	public String getEnglish() {
		switch (this) {
		case NEW:
			return "New";

		case MAINTENANCE:
			return "Maintenance";

		case BROKEN:
			return "Broken";

		case NEED_REPAIR:
			return "Need Repair";

		default:
			return null;
		}
	}
	
	public String getIndonesia() {
		switch (this) {
		case NEW:
			return "Baru";

		case MAINTENANCE:
			return "Pemeliharaan";

		case BROKEN:
			return "Rusak";

		case NEED_REPAIR:
			return "Butuh Perbaikan";

		default:
			return null;
		}
	}
}
