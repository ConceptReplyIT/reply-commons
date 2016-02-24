package it.reply.utils.validation;

/**
 * 
 * This class contains the list of the Regular Expressions used for the validation
 * 
 ************************************************************
 * Use this class paying attention: has not been tested yet *
 ************************************************************
 * 
 * @author Matteo Bassi
 *
 */
public class RegularExpressionList {

	/**
	 *  RULE:
	 *  	^				   				# Start of the line
	 *        ([A-Za-z0-9])					# must start with characters and number
	 *  	  +(?:[._-][A-Za-z0-9]+)*$      # Should contains characters and symbols in the list, a-z, A-Z, 0-9, ._-
	 *		$                 				# End of the line
	 */
	public static final String ALPHANUMERIC_DOT_UNDERSCORE_HYPHEN_PATTERN = "^([A-Za-z0-9])+(?:[._-][A-Za-z0-9]+)*$";
																			 
	
	/**
	 *  RULE:
	 *  	^                    # Start of the line
	 *  	  [a-z0-9_-]	     # Must contains characters and symbols in the list, a-z, A-Z, 0-9
	 *      $                    # End of the line
	 */
	public static final String ALPHANUMERIC_PATTERN = "^([A-Za-z0-9]+)*$";
	
	public static final String URL_PATTERN = "^[a-z0-9][a-z0-9-]+[a-z0-9]$";
	
	public static final String ALPHANUMERIC_HYPHEN_PATTERN = "^[a-zA-Z0-9][-a-zA-Z0-9]+$";

	public static final String ALPHANUMERIC_WHITESPACE_PATTERN = "^[a-zA-Z0-9][-a-zA-Z0-9 ]+$";
	
	/**
	 * 	RULE:
	 *		
	 *		^							# start of the line 
	 *		  [_A-Za-z0-9-]+			# must start with string in the bracket [ ], must contains one or more (+)
	 *		  (							# start of group #1
	 *		    \\.[_A-Za-z0-9-]+		# follow by a dot "." and string in the bracket [ ], must contains one or more (+)
	 *		  )*						# end of group #1, this group is optional (*)
	 *		    @						# must contains a "@" symbol
	 *			  [A-Za-z0-9]+       	# follow by string in the bracket [ ], must contains one or more (+)
	 *			    (					# start of group #2 - first level TLD checking
	 *			      \\.[A-Za-z0-9]+  	# follow by a dot "." and string in the bracket [ ], must contains one or more (+)
	 *			    )*					# end of group #2, this group is optional (*)
	 *			    (					# start of group #3 - second level TLD checking
	 *		          \\.[A-Za-z]{2,}  	# follow by a dot "." and string in the bracket [ ], with minimum length of 2
	 *			    )					# end of group #3
	 *		$							# end of the line
	 * 
	 */
	public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	
	
	public static final String PASSWORD_PATTERN = "^([A-Za-z0-9]+)*$";
	
	/**
	 * 	RULE: 
	 * 
	 * 		^		 					# start of the line
	 *		  #		 					# must constains a "#" symbols
  	 *		  (		 					# start of group #1
     *		    [A-Fa-f0-9]{6} 			# any strings in the list, with length of 6
  	 *		  	|						# ..or
  	 *	 		[A-Fa-f0-9]{3} 			# any strings in the list, with length of 3
	 *		  )							# end of group #1 
	 *		$							# end of the line
	 * 
	 */
	public static final String HEXADECIMAL_COLOR_CODE_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

	
	/**
	 *  RULE:
	 *        ^						# start of the line
     *          (					# start of group #1
   	 *			[01]?\\d\\d? 		# Can be one or two digits. If three digits appear, it must start either 0 or 1 e.g ([0-9], [0-9][0-9],[0-1][0-9][0-9])
   	 *			|					#    ...or
   	 *			2[0-4]\\d			#    start with 2, follow by 0-4 and end with any digit (2[0-4][0-9]) 
     *	 		|          			#    ...or
     *			25[0-5]     		#    start with 2, follow by 5 and ends with 0-5 (25[0-5]) 
 	 *			)					#  end of group #2
  	 *			\.            		#  follow by a dot "."
	 *         						# repeat with 3 times (3x)
	 *			$					#end of the line
	 */
	public static final String IPV4_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public static final String DN_PATTERN = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}$";
	
	/**
	 * Replace %s with the domain name (for example, using {@link String#format(String, Object...)}).
	 */
	public static final String DN_PATTERN_PREFIX = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.){1}(%s)$";
}
