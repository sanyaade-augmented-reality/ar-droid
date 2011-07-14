package ar.droid.admin.survey.response;

import java.util.Date;

import com.google.gson.annotations.Expose;

public abstract class Response {
	@Expose
	Date fecha;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
