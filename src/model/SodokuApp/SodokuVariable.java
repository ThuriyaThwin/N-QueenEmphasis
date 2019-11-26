package model.SodokuApp;

import engine.csp.Variable;
import util.XYLocation;

public class SodokuVariable extends Variable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = (prime * result) + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SodokuVariable other = (SodokuVariable) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	public XYLocation location;
	public int			value	= 0;
	public boolean fixed	= false;

	public SodokuVariable(XYLocation location) {
		super(location.toString());
		this.location = location;
	}

}