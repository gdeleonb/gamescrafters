require 'terminfo'

class Array
  def sentence_join connector
    case length
    when 0
      ''
    when 1
      self[0].to_s.dup
    when 2
      "#{self[0]} #{connector} #{self[1]}"
    else
      "#{self[0...-1].join(', ')}, #{connector} #{self[-1]}"
    end
  end
end

trap("INT") do
  puts
  exit
end

class TenToZero
  class Player
    def initialize default_name
      @name = get_name(default_name)

      print "Is #{@name} a human player? "
      human = gets.chomp.downcase
      allowed_responses = ['yes', 'y', 'no', 'n']
      until allowed_responses.include? human.downcase
        puts "Please respond with 'yes' or 'no'."
        print "Is #{@name} a human player? "
        human = gets.chomp.downcase
      end
      @human = (human == "yes" || human == "y")
    end

    def name
      @name
    end

    def human?
      @human
    end

    private

    def get_name default_name
      print "Please provide the name for #{default_name} (empty defaults to #{default_name}): "
      name = gets.chomp
      name == "" ? default_name : name
    end
  end

  def initialize
    ### Set Up ###
    system("clear" )|| system("cls")
    print_instructions # Start by explaining the rules of the game
    players_set_up # Getting the number of players, and their names
    set_first_turn # Now we get the names and figure out who starts
    system("clear" )|| system("cls")

    puts
    puts
    puts "### Game start! ###".center(TermInfo.screen_size[1])
    print_instructions true

    ### Game ###
    @score = 10
    while @score > 0
      puts "The score is at #{@score}. It is #{@players[@turn].name}'s turn:"
      play = @players[@turn].human? ? play_human_turn : play_computer_turn
      puts "#{@players[@turn].name}'s play is #{play}. New score is #{@score -= play}.'"
      puts
      @turn = (@turn + 1) % 2
    end

    screen_width = TermInfo.screen_size[1]
    puts "The score has hit 0!".center(screen_width)
    puts "#{@players[(@turn + 1) % 2].name} has triumphed over #{@players[@turn].name}!".center(screen_width)
    puts "Congratulations!".center(screen_width)
    puts
    puts
  end

  private

  def print_instructions reminder = false
    screen_width = TermInfo.screen_size[1]
    unless reminder
      puts
      puts
      puts "Welcome to 10 to 0!".center(screen_width)
    end
    puts "This is a two player game where the players take turns".center(screen_width)
    puts "subtracting 1 or 2 from 10 until the score reaches zero.".center(screen_width)
    puts "The player who brings it down to 0 wins!".center(screen_width)
    puts
    puts
  end

  def players_set_up
    @players = { 0 => Player.new("PLAYER 1"), 1 => Player.new("PLAYER 2") }
  end

  def set_first_turn
    print "Which player should go first? Please respond with '1', '2', or 'random': "
    first_turn = gets.chomp
    allowed_responses = ['1', '2', 'random']
    until allowed_responses.include? first_turn
      puts "Please respond with #{allowed_responses.sentence_join('or')}."
      print "Which player should go first? "
      first_turn = gets.chomp
    end
    @turn = first_turn == 'random' ? rand(2) : first_turn.to_i - 1
  end

  ### Game helper functions
  def play_human_turn
    allowed_moves = @score >= 2 ? ['1', '2'] : ['1']

    print "How much would #{@players[@turn].name} like to subtract? "
    play = gets.chomp

    until allowed_moves.include? play
      puts "That is an illegal move. Please respond with #{allowed_moves.sentence_join('or')}."
      print "How much would #{@players[@turn].name} like to subtract? "
      play = gets.chomp
    end

    play.to_i
  end

  def play_computer_turn
    @score >= 2 ? rand(2) + 1 : 1
  end
end

TenToZero.new
