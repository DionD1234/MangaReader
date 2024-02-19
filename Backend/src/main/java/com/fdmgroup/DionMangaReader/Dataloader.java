package com.fdmgroup.DionMangaReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdmgroup.DionMangaReader.model.Book;
import com.fdmgroup.DionMangaReader.model.BookmarkedBook;
import com.fdmgroup.DionMangaReader.model.Favourite;
import com.fdmgroup.DionMangaReader.dal.BookRepository;
import com.fdmgroup.DionMangaReader.dal.BookmarkedBookRepository;
import com.fdmgroup.DionMangaReader.dal.FavouriteRepository;
import com.fdmgroup.DionMangaReader.dal.UserRepository;
import com.fdmgroup.DionMangaReader.model.User;
import com.fdmgroup.DionMangaReader.security.RsaKeyProperties;
import com.fdmgroup.DionMangaReader.service.BookService;
import com.fdmgroup.DionMangaReader.service.UserService;

@EnableConfigurationProperties(RsaKeyProperties.class)
@Service
public class Dataloader implements ApplicationRunner
{

	private UserService userService;
	private BookService bookService;
	
	
	@Autowired
    public Dataloader(UserService userService, 
                      BookService bookService) 
	{
        super();

        this.userService = userService;
        this.bookService = bookService;
    }
	
	public int randomGenerator() {
        // Create a Random object
        Random random = new Random();
        
        // Generate a random number between 75 and 149
        int randomNumber = random.nextInt(75, 150);
        
        // Print the random number
        return randomNumber;
    }
	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		// public User(int userId, String email, String username, String password)
				User[] userList = {
						new User("fakeemail1@mail.com","username1","password1"),
						new User("fakeemail2@mail.com","username2","password2"),
						new User("fakeemail3@mail.com","username3","password3"),
						new User("fakeemail4@mail.com","username4","password4"),
						new User("fakeemail5@mail.com","username5","password5"),
						new User("fakeemail6@mail.com","username6","password6")
					};
				
				
				
				
				
		        Book[] bookList = {
		        		new Book(94, "https://mangadex.org/covers/6b958848-c885-4735-9201-12ee77abcb3c/7cac0992-5b89-4171-87cd-250c300a77d0.jpg.256.jpg", "SPY X FAMILY", "The master spy codenamed <Twilight> has spent most of his life on undercover missions, all for the dream of a better world. Yet one day he receives a particularly difficult order from command. For his mission, he must form a temporary family and start a new life?!"),
		        		new Book(445, "https://mangadex.org/covers/a96676e5-8ae2-425e-b549-7f15dd34a6d8/ef3ce0e5-19ff-4623-95fc-acf41afe1075.jpg.256.jpg", "Komi-san wa Komyushou Desu", "Komi-san is a beautiful and admirable girl that no one can take their eyes off of. Almost the whole school sees her as the cold beauty that's out of their league, but Tadano Hitohito knows the truth: she's just really bad at communicating with others. Komi-san, who wishes to fix this bad habit of hers, tries to improve it with the help of Tadano-kun by achieving her goal of having 100 friends."),
		        		new Book(360, "https://mangadex.org/covers/b73371d4-02dd-4db0-b448-d9afa3d698f1/e806d9d2-de63-4f9b-a5f4-618e335d2fdd.jpg.256.jpg", "Ao Ashi", "Aoi Ashito is a third year middle school student from Ehime. Behind his raw game hides his immense talent but Ashito suffers a huge setback because of his overly straightforward personality. One day the youth team manager of J1 club Tokyo City Esperion, Fukuda Tetsuya, appears in front of him. Fukuda sees his limitless potential and invites him to take part in his team's tryouts in Tokyo. The story of the boy who will revolutionize football in Japan rapidly begins to unfold."),
		        		new Book(17, "https://mangadex.org/covers/9e03b2ca-5191-44a6-88b6-c0cd49d06b51/2628b5d3-b121-4d47-ada7-c9dd3ba3e267.jpg.256.jpg", "I Sold My Life for 10,000 Yen per Year.", "A twenty-year-old with little hope for the future discovers a shop that buys lifespan, time, and health. This is a story dealing with the ensuing consequences."),
		        		new Book(115, "https://mangadex.org/covers/6da0b34b-db19-491a-b85c-6e31e0986f15/47e9c472-2019-46bb-b1be-5c609f181480.jpg.256.jpg", "Black Lagoon", "The story follows a team of mercenaries known as Lagoon Company, who smuggle goods in and around the seas of Southeast Asia. Their base of operations is located in the fictional city of Roanapur in Thailand, and they transport goods in the PT boat Black Lagoon. Lagoon Company does business with various clients, but has a particularly friendly relationship with the Russian crime syndicate Hotel Moscow. The team takes on a variety of missions - which may involve violent firefights, hand-to-hand combat, and nautical battles - in various Southeast Asian locations."),
		        		new Book(321, "https://mangadex.org/covers/9356fa3f-9ecd-4ff7-a7fe-241884680079/dba12108-a6e7-449f-bd17-69c6877c552f.jpg.256.jpg", "Skip Beat!", "Kyouko Mogami lived solely for her childhood friend Shoutaro \"Shou\" Fuwa. She follows Shou to Tokyo so that he may realize his dream of becoming a famous singer. When his dream is realized, Kyouko overhears the truth behind his decision to bringing her with him: he was using her as a maid. Shocked and enraged, Kyouko swears to take revenge by outdoing him in show business. With a new look and a new attitude, she joins LME, the agency where Ren Tsuruga (Shou's rival and the #1 actor of LME) works, in the hopes of achieving her goal of destroying Shou's pride. Along the way, she discovers her true self, makes new friends (and enemies), and finds herself at the heart of some interesting situations that will change her life, and the lives of all others involved, forever."),
		        		new Book(16, "https://mangadex.org/covers/d9e30523-9d65-469e-92a2-302995770950/0b815f57-43ad-4782-a56f-bf9d0d034147.jpg.256.jpg", "Monster", "Monster weaves the riveting story of brilliant Dr. Kenzo Tenma, a famous surgeon with a promising career at a leading hospital. Tenma risks his reputation and promising career to save the life of a critically wounded young boy. Unbeknownst to him, this child is destined for a terrible fate. A string of strange and mysterious murders begin to occur soon afterward, ones that professionally benefit Dr. Tenma, and he emerges as the primary suspect. Conspiracies, serial murders, and a scathing depiction of the underbelly of hospital politics are all masterfully woven together in this compelling manga thriller."),
		        		new Book(34, "https://mangadex.org/covers/baa95345-24fb-47a9-83e9-434ff671f968/3791f674-39c7-40b1-82e5-d7677365ccce.jpg.256.jpg", "Smoking Behind the Supermarket with You", "Sasaki, an overworked salaryman, goes through an average stressful day just for a single light at the end of the tunnel: Yamada, the always-cheerful clerk at his favorite convenience store. One day, Sasaki takes an extra amount of time at a company meeting, and by the time he gets to the convenience store Yamada has already clocked out. However, as he leaves, he notices a young woman with piercings smoking behind the store?"),
		        		new Book(98, "https://mangadex.org/covers/d90ea6cb-7bc3-4d80-8af0-28557e6c4e17/d885a28e-a5d3-4dc3-85e9-ca600f227b04.jpg.256.jpg", "Dungeon Meshi", "After his sister is devoured by a dragon and losing all their supplies in a failed dungeon raid, Laios and his party are determined to save his sister before she gets digested. Completely broke and having to resort to eating monsters as food, they meet a dwarf who will introduce them to the world of Dungeon Meshi - delicious cuisine made from ingredients such as the flesh of giant bats, walking mushrooms, or even screaming mandrakes."),
		        		new Book(23, "https://mangadex.org/covers/fed8c19c-9dec-4ef1-a187-af2c0c9cf17c/5f0fd818-ab19-4626-b7b3-c7440f63eb01.jpg.256.jpg", "Will You Marry Me Again If You Are Reborn?", "Toranosuke fell in love with his servant Kaoru. For twenty years, they slowly developed their love for each other, thus a Showa couple of clumsy husband and muscle brained wife was born. They spend a lot of time together, and they begin to talk in a nostalgic manner in Toranosuke's hospital room. Gentle memories of a deeply in love husband and wife."),
		        		new Book(60, "https://mangadex.org/covers/87ebd557-8394-4f16-8afe-a8644e555ddc/f5f9b3ed-1f42-4712-9409-e4f0e5a2d0c7.jpg.256.jpg", "Hirayasumi", "29-year-old freeter Ikuta Hiroto is a carefree young man without a love life, regular job, or any real worries about the future. He inherits an old house from the neighbourhood granny, where his 18-year-old cousin, Natsumi moves in with him to study art in Tokyo. This is a story about Hiroto and the people around him who struggle with life."),
		        		new Book(200, "https://mangadex.org/covers/32d76d19-8a05-4db0-9fc2-e0b0648fe9d0/e90bdc47-c8b9-4df7-b2c0-17641b645ee1.jpg.256.jpg", "Solo Leveling", "10 years ago, after “the Gate” that connected the real world with the monster world opened, some of the ordinary, everyday people received the power to hunt monsters within the Gate. They are known as “Hunters”. However, not all Hunters are powerful. My name is Sung Jin-Woo, an E-rank Hunter. I’m someone who has to risk his life in the lowliest of dungeons, the “World’s Weakest”. Having no skills whatsoever to display, I barely earned the required money by fighting in low-leveled dungeons… at least until I found a hidden dungeon with the hardest difficulty within the D-rank dungeons! In the end, as I was accepting death, I suddenly received a strange power, a quest log that only I could see, a secret to leveling up that only I know about! If I trained in accordance with my quests and hunted monsters, my level would rise. Changing from the weakest Hunter to the strongest S-rank Hunter!"),
		        		new Book(277, "https://mangadex.org/covers/319df2e2-e6a6-4e3a-a31c-68539c140a84/a845455f-52d0-4cc4-92a8-bd5bad7d69b9.jpg.256.jpg", "Slam Dunk!", "Winning isn't everything in the game of basketball, but who wants to come in second? It takes dedication and discipline to be the best, and the Shohoku High hoops team wants to be just that. They have one last year to make their captain's dream of reaching the finals come true–will they do it? Takehiko Inoue's legendary beloved basketball manga is finally here and the tale of a lifetime is in your hands. Hanamichi Sakuragi's got no game with girls—none at all! It doesn't help that he's known for throwing down at a moment's notice and always coming out on top. A hopeless bruiser, he's been rejected by 50 girls in a row! All that changes when he meets the girl of his dreams, Haruko, and she's actually not afraid of him! When she introduces him to the game of basketball, his life is changed forever…"),
		        		new Book(223, "https://mangadex.org/covers/6e3553b9-ddb5-4d37-b7a3-99998044774e/e4504532-62f8-467c-9f90-d6390b5f3126.jpg.256.jpg", "ReLIFE", "Kaizaki Arata is a 27-year-old unemployed man who, after quitting his last job after three months, has failed every interview since. Enter Yoake Ryou; representative of the ReLIFE Organization. He offers Kaizaki a pill that changes his appearance to that of his 17-year-old self; thus, Kaizaki becomes the subject of a one-year experiment in which he begins his life as a third year high school student once again."),
		        		new Book(1448, "https://mangadex.org/covers/f7888782-0727-49b0-95ec-a3530c70f83b/c0e294d0-2915-4b7d-93e0-1ac787a7c7a7.jpg.256.jpg", "Hajime No Ippo - The First Step", "Makunouchi Ippo is an ordinary high school student in Japan. Since he spends most of his time away from school helping his mother run the family business, he doesn't get to enjoy his younger years like most teenagers. Always a target for bullying at school, Ippo's life is one of hardship. One of these after-school bullying sessions turns Ippo's life around for the better, as he is saved by a boxer named Takamura. He decides to follow in Takamura's footsteps and train to become a boxer, giving his life direction and purpose. Ippo's path to perfecting his pugilistic prowess is just beginning."),
		        		new Book(21, "https://mangadex.org/covers/67bd081f-1c40-4ae2-95a2-6af29de4fc01/29dcabe0-9efe-4575-9fcf-36be55363591.jpg.256.jpg", "The Horizon", "Two children unexpectedly meet in the midst of war. After running away from the chaos, they come across a long empty road. With no adults to rely on, the two strangers, now become friends, walk alongside each other to see what's at the end of the road. All they hope is to keep being able to move forward. Just what kind of trials and tragedy awaits them during their journey for survival?"),
		        		new Book(112, "https://mangadex.org/covers/58be6aa6-06cb-4ca5-bd20-f1392ce451fb/5951267f-f863-4e1b-b1fc-a5cfb56faaf8.jpg.256.jpg", "Yotsuba&!", "Yotsuba is a strange little girl with a big personality! Even in the most trivial, unremarkable encounters, Yotsuba's curiosity and enthusiasm quickly turns the everyday into the extraordinary!"),
		        		new Book(198, "https://mangadex.org/covers/9a414441-bbad-43f1-a3a7-dc262ca790a3/aeb88211-faa2-4f9f-adee-a8e4d814d51a.jpg.256.jpg", "Omniscient Reader's Viewpoint", "Dokja was an average office worker whose sole interest was reading his favorite web novel 'Three Ways to Survive the Apocalypse.' But when the novel suddenly becomes reality, he is the only person who knows how the world will end. Armed with this realization, Dokja uses his understanding to change the course of the story, and the world, as he knows it."),
		        		new Book(180, "https://mangadex.org/covers/4ada20eb-085a-491a-8c49-477ab42014d7/beff67b2-b671-4018-894c-33f7cb068f48.jpg.256.jpg", "The Beginning After the End", "King Grey has unrivaled strength, wealth, and prestige in a world governed by martial ability. However, solitude lingers closely behind those with great power. Beneath the glamorous exterior of a powerful king lurks the shell of man, devoid of purpose and will. Reincarnated into a new world filled with magic and monsters, the king has a second chance to relive his life. Correcting the mistakes of his past will not be his only challenge, however. Underneath the peace and prosperity of the new world is an undercurrent threatening to destroy everything he has worked for, questioning his role and reason for being born again."),
		        		new Book(336, "https://mangadex.org/covers/d7037b2a-874a-4360-8a7b-07f2899152fd/40f3e267-6d83-4964-aedc-43d82b942013.jpg.256.jpg", "Mairimashita! Iruma-kun", "Iruma Suzuki, fourteen years old, can’t say no to any request. His irresponsible parents make him work dangerous job after dangerous job to support them, and then one day they sell him to a demon! But, much to his surprise, the demon wants to adopt Iruma as his grandson? Unable to refuse his request, Iruma becomes the grandson of the great demon Sullivan. His new doting demon grandfather enrolls him in Demon School Babyls – where Sullivan is coincidentally the principal. Thus begins Iruma-kun's extraordinary school life among the otherworldly, meeting many colorful demons, taking on daunting challenges, and facing his true self as he rises to become someone great."),
		        		new Book(142, "https://mangadex.org/covers/9ed16bc9-f570-4e71-8dda-aebc098b683b/8a917622-1853-47b7-9114-997da510a8a2.jpg.256.jpg", "The Legend of the Northern Blade", "When the world was plunged into darkness by the ‘Silent Night’, Martial Artists from all over the place gathered to form the ‘Northern Heavenly Sect’. With overwhelming strength from the Northern Heavenly Sect, the Silent Night was pushed away and the people began to enjoy peace once more. However, as time passed the martial artists from the mainlands began to conspire against the ‘Northern Heavenly Sect’, and eventually caused the death of the Fourth Generation Sect Leader, Jin Kwan-Ho, destroying the sect with it. As everyone left the sect, Jin Kwan-Ho’s only son, Jin Mu-Won was left behind. Mu-Won has never learned anything about martial arts, but he eventually finds the Martial Techniques secretly left behind by his father and begins to acquire the martial arts of the Northern Heavenly Sect. Then one day, a mysterious girl appears before Mu-Won…!!"),
		        		new Book(79, "https://mangadex.org/covers/10b4c1bb-f6bd-4dd7-a0aa-c895ad8c840d/7886e658-44c2-4ba9-9cde-785824960bed.png.256.jpg", "The SSS-Ranker Returns", "What would you do with the chance to become far greater than your greatest rivals… before they could even start? When a dominant guild gangs up on Rokan to humiliate him and destroy his hard-earned achievements in the world's most realistic fantasy VR game, he becomes desperate for revenge. But just when Rokan thinks he's lost it all, a twist in space-time sends him back to right before the game is even launched. Now, this former SSS-class ranker sees the perfect opportunity to return to glory!"),
		        		new Book(192, "https://mangadex.org/covers/6e4805a6-75ab-462d-883c-4ddedb8e4df6/664dfc87-4464-486e-bc87-74156805fd18.jpg.256.jpg", "Nano Machine", "After being held in disdain and having his life put in danger, an orphan from the Demonic Cult, Cheon Yeo-Woon, has an unexpected visit from his descendant from the future who inserts a nano machine into Cheon Yeo-Woon's body, which drastically changes Cheon Yeo-Woon's life after its activation. The story of Cheon Yeo-Woon's journey of bypassing the Demonic Cult and rising to become the best martial artist has just begun."),
		        		new Book(81, "https://mangadex.org/covers/58167656-a065-4b49-b499-bd73d00555d5/4fbfc611-19f8-4a33-b8f3-8535c2779288.jpg.256.jpg", "Pick Me Up!", "In the mobile gacha game infamous for its atrocious difficulty, the Master ranked 5th in the world, 'Loki', loses consciousness while raiding the Dungeon. 'What? I'm a game character now?' After waking up, 'Loki' realizes he has turned into a Level 1, 1-Star Hero—'Han Yslat'. To return to Earth, he must lead the newbie Master and heroes and clear the 100th floor of the Dungeon! 'You messed with the wrong guy.' This is the story of hard carrying by Master Loki who never accepts even a single defeat."),
		        		new Book(62, "https://mangadex.org/covers/773c2211-750b-4fff-bd64-c914986e4637/566923c4-f93e-4548-b3c7-4d1372e29590.jpg.256.jpg", "Doom Breaker", "Zephyr is the last human fighting evil in a world abandoned by the gods. When he is killed in battle by Tartarus, the god of destruction, all hope for humanity seems lost. But Zephyr's fate is not sealed -- the gods who find his battles entertaining have gifted him a second chance at life, as he is sent ten years into the past, back to when he was a slave instead of the most powerful human alive. Can Zephyr get his revenge against Tartarus and save the woman he loves, or is he doomed to repeat the past?")
		            }; 
		        
		        Favourite[] favouriteList = {
		        	    new Favourite(1, 1),
		        	    new Favourite(1, 4),
		        	    new Favourite(1, 5),
		        	    new Favourite(2, 3),
		        	    new Favourite(3, 2),
		        	    new Favourite(4, 6),
		        	    new Favourite(5, 6),
		        	    new Favourite(5, 5),
		        	    new Favourite(7, 1),
		        	    new Favourite(10, 2),
		        	    new Favourite(11, 1),
		        	    new Favourite(11, 3),
		        	    new Favourite(11, 5),
		        	    new Favourite(12, 4),
		        	    new Favourite(13, 6),
		        	    new Favourite(13, 1),
		        	    new Favourite(14, 2),
		        	    new Favourite(15, 3),
		        	    new Favourite(16, 4),
		        	    new Favourite(16, 5),
		        	    new Favourite(16, 6),
		        	    new Favourite(17, 1),
		        	    new Favourite(17, 3),
		        	    new Favourite(18, 5),
		        	    new Favourite(19, 4),
		        	    new Favourite(20, 1),
		        	    new Favourite(20, 2),
		        	    new Favourite(21, 6),
		        	    new Favourite(21, 3),
		        	    new Favourite(22, 1),
		        	    new Favourite(24, 3),
		        	    new Favourite(24, 2),
		        	    new Favourite(24, 4),
		        	    new Favourite(24, 5),
		        	    new Favourite(25, 6)
		        	};
		        
		        BookmarkedBook[] bookmarkedList = {
		        	    new BookmarkedBook(1, 4, 10),
		        	    new BookmarkedBook(1, 1, 1),
		        	    new BookmarkedBook(2, 2, 15),
		        	    new BookmarkedBook(3, 3, 14),
		        	    new BookmarkedBook(4, 5, 13),
		        	    new BookmarkedBook(5, 6, 8),
		        	    new BookmarkedBook(7, 2, 11),
		        	    new BookmarkedBook(10, 3, 7),
		        	    new BookmarkedBook(11, 4, 6),
		        	    new BookmarkedBook(11, 6, 9),
		        	    new BookmarkedBook(24, 6, 9),
		        	    new BookmarkedBook(13, 5, 1),
		        	    new BookmarkedBook(14, 3, 9),
		        	    new BookmarkedBook(15, 1, 9),
		        	    new BookmarkedBook(16, 2, 11),
		        	    new BookmarkedBook(17, 6, 7),
		        	    new BookmarkedBook(18, 4, 1),
		        	    new BookmarkedBook(19, 5, 7),
		        	    new BookmarkedBook(20, 2, 6),
		        	    new BookmarkedBook(21, 3, 10),
		        	    new BookmarkedBook(22, 4, 11),
		        	    new BookmarkedBook(24, 6, 9),
		        	    new BookmarkedBook(25, 5, 10),
		        	    new BookmarkedBook(1, 3, 1),
		        	    new BookmarkedBook(9, 6, 2),
		        	    new BookmarkedBook(2, 2, 3),
		        	    new BookmarkedBook(24, 4, 4),
		        	    new BookmarkedBook(24, 1, 12),
		        	    new BookmarkedBook(25, 1, 7),
		        	    new BookmarkedBook(25, 6, 1),
		        	    new BookmarkedBook(24, 2, 6),
		        	    new BookmarkedBook(19, 4, 8),
		        	    new BookmarkedBook(18, 5, 11),
		        	    new BookmarkedBook(17, 3, 7),
		        	    new BookmarkedBook(16, 6, 6),
		        	    new BookmarkedBook(15, 1, 4),
		        	    new BookmarkedBook(14, 6, 10),
		        	    new BookmarkedBook(13, 5, 6),
		        	    new BookmarkedBook(12, 1, 7),
		        	    new BookmarkedBook(11, 3, 9),
		        	    new BookmarkedBook(10, 4, 10),
		        	    new BookmarkedBook(9, 1, 9),
		        	    new BookmarkedBook(8, 2, 6),
		        	    new BookmarkedBook(6, 4, 7),
		        	    new BookmarkedBook(5, 5, 10),
		        	    new BookmarkedBook(4, 1, 14),
		        	    new BookmarkedBook(3, 6, 12),
		        	    new BookmarkedBook(2, 3, 11),
		        	    new BookmarkedBook(1, 4, 11),
		        	    new BookmarkedBook(1, 5, 10),
		        	    new BookmarkedBook(25, 6, 8),
		        	    new BookmarkedBook(25, 2, 2),
		        	    new BookmarkedBook(23, 3, 1),
		        	    new BookmarkedBook(23, 1, 3)
		        	};
		        

		        for (User user : userList) {
		            userService.register(user);
		        }
		        for (Book book : bookList) {
		            bookService.save(book);
		        }
		        List<User> userResultList = userService.findAll();

		        // Link bookmarks and favorites to users and books
		        for (User user : userResultList) {
		            for (BookmarkedBook bookmarkedbook : bookmarkedList) {
		                if (user.getUserId() == bookmarkedbook.getUserId()) {
		                    user.addBookmarkedBook(bookmarkedbook);
		                }
		            }
		            for (Favourite favBook : favouriteList) {
		                if (user.getUserId() == favBook.getUserId()) {
		                    user.addFavourite(favBook);
		                }
		            }
		            userService.update(user);
		            
		        }
		        List<Book> bookResultList = bookService.findAll();
		        for (Book book : bookResultList) {
		            for (BookmarkedBook bookmarkedbook : bookmarkedList) {
		                if (book.getBookId() == bookmarkedbook.getBookId()) {
		                    book.addBookmarkedBook(bookmarkedbook);
		                }
		            }
		            for (Favourite favBook : favouriteList) {
		                if (book.getBookId() == favBook.getBookId()) {
		                    book.addFavourite(favBook);
		                }
		            }
		            bookService.update(book);

		        }
		    }
		
}
