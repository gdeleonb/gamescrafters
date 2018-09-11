VALUE_TRANSL = { "L" => "losing", "W" => "winning", "U" => "undecided", "T" => "tie" }

def generate_moves(pos)
  pos >= 2 ? [1, 2] : [1]
end

def do_move(pos, move)
  pos - move
end

def primitive(pos)
  pos == 0 ? "L" : "U"
end

$solved_positions = Hash.new { |hash, key| solve(key) }

def solve(pos)
  value = primitive(pos)
  return value if value != "U"

  moves = generate_moves(pos)
  numWinningChildren = 0
  moves.each do |m|
    outcome = $solved_positions[do_move(pos, m)]
    return "W" if outcome == "L"
    numWinningChildren += 1 if outcome == "W"
  end

  numWinningChildren == moves.length ? "L" : "T"
end


(0..100).each do |pos|
  puts "Position #{pos} is a #{VALUE_TRANSL[$solved_positions[pos]]} position."
end
