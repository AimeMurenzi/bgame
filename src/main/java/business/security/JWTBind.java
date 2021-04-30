/**
 * @Author: Aimé
 * @Date:   2021-04-26 19:14:07
 * @Last Modified by:   Aimé
 * @Last Modified time: 2021-04-30 08:46:50
 */
package business.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface JWTBind {

}
