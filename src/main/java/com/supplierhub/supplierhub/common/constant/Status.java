package com.supplierhub.supplierhub.common.constant;

public enum Status {
	REQUESTED, REPAIRING, BROUGHT_TO_VENDOR, DONE;
	
	public String getEnglish() {
		switch (this) {
		case REQUESTED:
			return "Requested";

		case REPAIRING:
			return "Repairing";

		case BROUGHT_TO_VENDOR:
			return "Brought To Vendor";

		case DONE:
			return "Done";

		default:
			return null;
		}
	}
	
	public String getIndonesia() {
		switch (this) {
		case REQUESTED:
			return "Diminta";

		case REPAIRING:
			return "Sedang Diperbaiki";

		case BROUGHT_TO_VENDOR:
			return "Di bawa Ke Vendor";

		case DONE:
			return "Selesai";

		default:
			return null;
		}
	}
}
