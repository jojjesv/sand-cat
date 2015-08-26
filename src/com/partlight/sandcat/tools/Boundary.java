package com.partlight.sandcat.tools;

public interface Boundary {

	public static class BoundaryUtils {
		public static boolean isIntersecting(Boundary boundary1, Boundary boundary2) {

			if (boundary1.getBoundaryX() > boundary2.getBoundaryX() - boundary1.getBoundaryWidth())
				if (boundary1.getBoundaryY() > boundary2.getBoundaryY() - boundary1.getBoundaryHeight())
					if (boundary1.getBoundaryX() < boundary2.getBoundaryX() + boundary2.getBoundaryWidth())
						if (boundary1.getBoundaryY() < boundary2.getBoundaryY() + boundary2.getBoundaryHeight())
							return true;

			return false;
		}
	}

	public float getBoundaryHeight();

	public float getBoundaryWidth();

	public float getBoundaryX();

	public float getBoundaryY();
}