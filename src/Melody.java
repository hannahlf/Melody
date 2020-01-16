
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Melody {
	Queue<Note> songQueue = new LinkedList<Note>();
	Stack<Note> songStack = new Stack<Note>();
	public Melody(Queue<Note> song) {
		this.songQueue = song;
	}
	public double getTotalDuration() {
		double total = 0;
		boolean repeat = false;
		Queue<Note> songS = new LinkedList<>();
		songS = songQueue;
		for(int i = 0; i < songS.size(); i++) 
		{
			Note note = songS.remove();

			songS.add(note);
			if(note.isRepeat() == true)
			{	
				repeat = !repeat;
			}
			if(note.isRepeat() == false && repeat == false)
			{
				total = total + (double)note.getDuration();
			}else
			{
				total = total + 2*(double)note.getDuration();
			}
		}
		return total;
	}

	public String toString() {

		String songNotes = "";
		Queue<Note> songS = new LinkedList<>();
		while(!songQueue.isEmpty()) {
			Note note = songQueue.remove();
			songNotes = songNotes + note.getDuration() + " " + note.getPitch() + " " + note.getOctave() + 
					" " + note.getAccidental() + " " + note.isRepeat() + "\n";
			songS.add(note);

		}
		songQueue = songS;
		return songNotes;
	}


	public void changeTempo(double ratio) {
		for(int i = 0; i < songQueue.size(); i++)
		{
			Note note = songQueue.remove();
			double change = note.getDuration();
			change = change*ratio;
			note.setDuration(change);

			songQueue.add(note);
		}
	}


	public void reverse() {
		Stack<Note> reversed = new Stack<>();
		for(int i = 0; i < songQueue.size(); i++)
		{
			Note note = songQueue.remove();

			reversed.push(note);
		}
		for(int i = 0; i < reversed.size(); i++)
		{
			Note note = reversed.pop();

			songQueue.add(note);
		}
	}


	public void append(Melody other) {
		for(int i = 0; i < other.songQueue.size(); i++)
		{
			Note note = other.songQueue.remove();

			songQueue.add(note);
		}

	}

	public void play() {
		//put repeating section into a temporary queue instead of double at end of real song play temp queue
		boolean repeat = false;
		//Queue<Note> songS = new LinkedList<>();
		//songS = songM;
		Queue<Note> repeated = new LinkedList<>();
		for(int i = 0; i < songQueue.size(); i++) 
		{
			Note note = songQueue.remove();

			songQueue.add(note); 
			if(note.isRepeat() == true)
			{	
				repeat = !repeat;
			}
			if(note.isRepeat() == false && repeat == false)
			{
				note.play();
			}else
			{
				repeated.add(note);
				note.play();
			}
			if(note.isRepeat() == true && repeat == false )
			{
				while(!repeated.isEmpty())
				{
					note = repeated.remove();
					note.play();
				}
			}
		}

	}



}
