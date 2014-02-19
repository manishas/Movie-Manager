package ie.simo.movies.domain.award;


public enum AwardState {

	SHOW_INTRO,
	SHOW_NOMINEES,
	AND_THE_WINNER_IS,
	SHOW_WINNER,
	USER_COMMENT,
	END;
	
	public static AwardState getNextState(AwardState state)
	{
		switch (state) {
			case SHOW_INTRO: 		return SHOW_NOMINEES;
			case SHOW_NOMINEES:		return AND_THE_WINNER_IS;
			case AND_THE_WINNER_IS:	return SHOW_WINNER;
			case SHOW_WINNER:		return USER_COMMENT;
			case USER_COMMENT:		return END;
			default:				return END;
		}
	}
}
