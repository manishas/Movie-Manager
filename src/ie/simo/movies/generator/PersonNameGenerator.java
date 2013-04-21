package ie.simo.movies.generator;

import ie.simo.movies.util.RandomNumberProvider;

import java.util.Random;

public class PersonNameGenerator {
	private Random rng = RandomNumberProvider.getInstance();
	
	private static String[] maleName = {  "James", "John", "Robert", "Michael", "William",
		"David", "Richard", "Charles", "Joseph", "Thomas", "Christopher",
		"Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven",
		"Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason",
		"Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey", "Frank",
		"Scott", "Eric", "Stephen", "Andrew", "Raymond", "Gregory",
		"Joshua", "Jerry", "Dennis", "Walter", "Patrick", "Peter",
		"Harold", "Douglas", "Henry", "Carl", "Arthur", "Ryan", "Roger",
		"Joe", "Juan", "Jack", "Albert", "Jonathan", "Justin", "Terry",
		"Gerald", "Keith", "Samuel", "Willie", "Ralph", "Lawrence",
		"Nicholas", "Roy", "Benjamin", "Bruce", "Brandon", "Adam", "Harry",
		"Fred", "Wayne", "Billy", "Steve", "Louis", "Jeremy", "Aaron",
		"Randy", "Howard", "Eugene", "Carlos", "Russell", "Bobby",
		"Victor", "Martin", "Ernest", "Phillip", "Todd", "Jesse", "Craig",
		"Alan", "Shawn", "Clarence", "Sean", "Philip", "Chris", "Johnny",
		"Earl", "Jimmy", "Antonio", "Danny", "Bryan", "Tony", "Luis",
		"Mike", "Stanley", "Leonard", "Nathan", "Dale", "Manuel", "Rodney",
		"Curtis", "Norman", "Allen", "Marvin", "Vincent", "Glenn",
		"Jeffery", "Travis", "Jeff", "Chad", "Jacob", "Lee", "Melvin",
		"Alfred", "Kyle", "Francis", "Bradley", "Jesus", "Herbert",
		"Frederick", "Ray", "Joel", "Edwin", "Don", "Eddie", "Ricky",
		"Troy", "Randall", "Barry", "Alexander", "Bernard", "Mario",
		"Leroy", "Francisco", "Marcus", "Micheal", "Theodore", "Clifford",
		"Miguel", "Oscar", "Jay", "Jim", "Tom", "Calvin", "Alex", "Jon",
		"Ronnie", "Bill", "Lloyd", "Tommy", "Leon", "Derek", "Warren",
		"Darrell", "Jerome", "Floyd", "Leo", "Alvin", "Tim", "Wesley",
		"Gordon", "Dean", "Greg", "Jorge", "Dustin", "Pedro", "Derrick",
		"Dan", "Lewis", "Zachary", "Corey", "Herman", "Maurice", "Vernon",
		"Roberto", "Clyde", "Glen", "Hector", "Shane", "Ricardo", "Sam",
		"Rick", "Lester", "Brent", "Ramon", "Charlie", "Tyler", "Gilbert",
		"Gene", "Marc", "Reginald", "Ruben", "Brett", "Angel", "Nathaniel",
		"Rafael", "Leslie", "Edgar", "Milton", "Raul", "Ben", "Chester",
		"Cecil", "Duane", "Franklin", "Andre", "Elmer", "Brad", "Gabriel",
		"Ron", "Mitchell", "Roland", "Arnold", "Harvey", "Jared", "Adrian",
		"Karl", "Cory", "Claude", "Erik", "Darryl", "Jamie", "Neil",
		"Jessie", "Christian", "Javier", "Fernando", "Clinton", "Ted",
		"Mathew", "Tyrone", "Darren", "Lonnie", "Lance", "Cody", "Julio",
		"Kelly", "Kurt", "Allan", "Nelson", "Guy", "Clayton", "Hugh",
		"Max", "Dwayne", "Dwight", "Armando", "Felix", "Jimmie", "Everett",
		"Jordan", "Ian", "Wallace", "Ken", "Bob", "Jaime", "Casey",
		"Alfredo", "Alberto", "Dave", "Ivan", "Johnnie", "Sidney", "Byron",
		"Julian", "Isaac", "Morris", "Clifton", "Willard", "Daryl", "Ross",
		"Virgil", "Andy", "Marshall", "Salvador", "Perry", "Kirk",
		"Sergio", "Marion", "Tracy", "Seth", "Kent", "Terrance", "Rene",
		"Eduardo", "Terrence", "Enrique", "Freddie", "Wade" };

	private static String[] femaleName = { "Mary", "Patricia", "Linda", "Barbara",
		"Elizabeth", "Jennifer", "Maria", "Susan", "Margaret", "Dorothy",
		"Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna",
		"Carol", "Ruth", "Sharon", "Michelle", "Laura", "Sarah",
		"Kimberly", "Deborah", "Jessica", "Shirley", "Cynthia", "Angela",
		"Melissa", "Brenda", "Amy", "Anna", "Rebecca", "Virginia",
		"Kathleen", "Pamela", "Martha", "Debra", "Amanda", "Stephanie",
		"Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances",
		"Ann", "Joyce", "Diane", "Alice", "Julie", "Heather", "Teresa",
		"Doris", "Gloria", "Evelyn", "Jean", "Cheryl", "Mildred",
		"Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly",
		"Nicole", "Judy", "Christina", "Kathy", "Theresa", "Beverly",
		"Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn",
		"Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline",
		"Wanda", "Bonnie", "Julia", "Ruby", "Lois", "Tina", "Phyllis",
		"Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin",
		"Peggy", "Crystal", "Gladys", "Rita", "Dawn", "Connie", "Florence",
		"Tracy", "Edna", "Tiffany", "Carmen", "Rosa", "Cindy", "Grace",
		"Wendy", "Victoria", "Edith", "Kim", "Sherry", "Sylvia",
		"Josephine", "Thelma", "Shannon", "Sheila", "Ethel", "Ellen",
		"Elaine", "Marjorie", "Carrie", "Charlotte", "Monica", "Esther",
		"Pauline", "Emma", "Juanita", "Anita", "Rhonda", "Hazel", "Amber",
		"Eva", "Debbie", "April", "Leslie", "Clara", "Lucille", "Jamie",
		"Joanne", "Eleanor", "Valerie", "Danielle", "Megan", "Alicia",
		"Suzanne", "Michele", "Gail", "Bertha", "Darlene", "Veronica",
		"Jill", "Erin", "Geraldine", "Lauren", "Cathy", "Joann",
		"Lorraine", "Lynn", "Sally", "Regina", "Erica", "Beatrice",
		"Dolores", "Bernice", "Audrey", "Yvonne", "Annette", "June",
		"Samantha", "Marion", "Dana", "Stacy", "Ana", "Renee", "Ida",
		"Vivian", "Roberta", "Holly", "Brittany", "Melanie", "Loretta",
		"Yolanda", "Jeanette", "Laurie", "Katie", "Kristen", "Vanessa",
		"Alma", "Sue", "Elsie", "Beth", "Jeanne", "Vicki", "Carla", "Tara",
		"Rosemary", "Eileen", "Terri", "Gertrude", "Lucy", "Tonya", "Ella",
		"Stacey", "Wilma", "Gina", "Kristin", "Jessie", "Natalie", "Agnes",
		"Vera", "Willie", "Charlene", "Bessie", "Delores", "Melinda",
		"Pearl", "Arlene", "Maureen", "Colleen", "Allison", "Tamara",
		"Joy", "Georgia", "Constance", "Lillie", "Claudia", "Jackie",
		"Marcia", "Tanya", "Nellie", "Minnie", "Marlene", "Heidi",
		"Glenda", "Lydia", "Viola", "Courtney", "Marian", "Stella",
		"Caroline", "Dora", "Jo", "Vickie", "Mattie", "Terry", "Maxine",
		"Irma", "Mabel", "Marsha", "Myrtle", "Lena", "Christy", "Deanna",
		"Patsy", "Hilda", "Gwendolyn", "Jennie", "Nora", "Margie", "Nina",
		"Cassandra", "Leah", "Penny", "Kay", "Priscilla", "Naomi",
		"Carole", "Brandy", "Olga", "Billie", "Dianne", "Tracey", "Leona",
		"Jenny", "Felicia", "Sonia", "Miriam", "Velma", "Becky", "Bobbie",
		"Violet", "Kristina", "Toni", "Misty", "Mae", "Shelly", "Daisy",
		"Ramona", "Sherri", "Erika", "Katrina", "Claire" };
	
	private static String[] surname = {
			"Smith","Johnson","Williams","Brown","Jones","Miller","Davis","Garc�a","Rodr�guez","Wilson",
			"Mart�nez","Anderson","Taylor","Thomas","Hern�ndez","Moore","Martin","Jackson","Thompson",
			"White","L�pez","Lee","Gonz�lez","Harris","Clark","Lewis","Robinson","Walker","P�rez","Hall",
			"Young","Allen","S�nchez","Wright","King","Scott","Green","Baker","Adams","Nelson","Hill",
			"Ram�rez","Campbell","Mitchell","Roberts","Carter","Phillips","Evans","Turner","Torres",
			"Parker","Collins","Edwards","Stewart","Flores","Morris","Norton","Murphy","Rivera","Cook",
			"Rogers","Morgan","Peterson","Cooper","Reed","Bailey","Bell","G�mez","Kelly","Howard","Ward",
			"Cox","D�az","Richardson","Wood","Watson","Brooks","Bennett","Gray","James","Reyes","Cruz",
			"Hughes","Price","Myers","Long","Foster","Sanders","Ross","Morales","Powell","Sullivan",
			"Russell","Ortiz","Jenkins","Guti�rrez","Perry","Butler","Barnes","Fisher"
	};
	
	String getMaleName(){
		String first = getMaleFirstName();
		String last = random(surname);
		
		return first + " " + last;
	}
	
	String getfemaleName(){
		String first = getFemaleFirstName();
		String last = random(surname);
		
		return first + " " + last;
	}
	
	/**
	 * Get a male or female first name
	 * @return a first name
	 */
	public String getFirstName(){
		return rng.nextBoolean() ? getMaleFirstName() : getFemaleFirstName();
	}

	public String getMaleFirstName() {
		return random(maleName);
	}
	
	public String getFemaleFirstName() {
		return random(femaleName);
	}
	
	/**
	 * return random element from the array
	 * @param array
	 */
	private String random(String [] array){
		return array[rng.nextInt(array.length)];
	}
}
