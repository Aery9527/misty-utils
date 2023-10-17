/**
 * This package is designed to store interfaces that enhance Functional Interface (FI) functionality.
 * Because actions within FI often throw exceptions, the compiler will prompt you to use try-catch.
 * However, many times, we want to directly propagate the internal errors of FI outward, without adding an additional layer of try-catch.
 * Therefore, the FIs within this package can be used to directly throw internal errors without the need for an additional try-catch layer.
 */
package org.misty.utils.ex.fi;
