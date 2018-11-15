pragma solidity 0.4.21; 

contract Ownable {
  address public owner;

  event OwnershipTransferred(address indexed previousOwner, address indexed newOwner);

  function Ownable() public {
    owner = msg.sender;
  }

  modifier onlyOwner() {
    require(msg.sender == owner);
    _;
  }

  function transferOwnership(address newOwner) public onlyOwner {
    require(newOwner != address(0));
    emit OwnershipTransferred(owner, newOwner);
    owner = newOwner;
  }
}

contract Pausable is Ownable {
  event Pause();
  event Unpause();

  bool public paused = false;

  modifier whenNotPaused() {
    require(!paused);
    _;
  }

  modifier whenPaused() {
    require(paused);
    _;
  }

  function pause() onlyOwner whenNotPaused public {
    paused = true;
    emit Pause();
  }

  function unpause() onlyOwner whenPaused public {
    paused = false;
    emit Unpause();
  }
}


contract ERC20 {
  string public name;
  string public symbol;
  uint8 public decimals;
  uint public totalSupply;  
  function ERC20(string _name, string _symbol, uint8 _decimals) public {
    name = _name;
    symbol = _symbol;
    decimals = _decimals;
  }
  function balanceOf(address who) public view returns (uint);
  function transfer(address to, uint value) public returns (bool);
  function allowance(address owner, address spender) public view returns (uint);
  function transferFrom(address from, address to, uint value) public returns (bool);
  function approve(address spender, uint value) public returns (bool);
  event Transfer(address indexed from, address indexed to, uint value);
  event Approval(address indexed owner, address indexed spender, uint value);
}


contract GomoToken is Pausable, ERC20 {

  mapping(address => uint) balances;
  mapping (address => mapping (address => uint)) internal allowed;

  function GomoToken() ERC20("AD Network Coin", "ADC", 18) public {
    totalSupply = 1000000000 * 10 ** uint(decimals);  // Update total supply with the decimal amount
    balances[msg.sender] = totalSupply;                   // Give the creator all initial tokens
  }

  function transfer(address _to, uint _value) whenNotPaused public returns (bool) {
    require(_to != address(0));
    require(_value <= balances[msg.sender]);

	assert(_value <= balances[msg.sender]);
    balances[msg.sender] = balances[msg.sender] - _value;
	
	assert(balances[_to] + _value >= balances[_to]);
    balances[_to] = balances[_to] + _value;
	
    emit Transfer(msg.sender, _to, _value);
    return true;
  }

  function balanceOf(address _owner) public view returns (uint balance) {
    return balances[_owner];
  }

  function transferFrom(address _from, address _to, uint _value) public whenNotPaused returns (bool) {
    require(_to != address(0));
    require(_value <= balances[_from]);
    require(_value <= allowed[_from][msg.sender]);
	
    assert(_value <= balances[_from]);
    balances[_from] = balances[_from] - _value;
	
	assert(balances[_to] + _value >= balances[_to]);
    balances[_to] = balances[_to] + _value;
	
	assert(_value <= allowed[_from][msg.sender]);
    allowed[_from][msg.sender] = allowed[_from][msg.sender] - _value;
	
    emit Transfer(_from, _to, _value);
    return true;
  }

  function approve(address _spender, uint _value) public whenNotPaused returns (bool) {
    allowed[msg.sender][_spender] = _value;
    emit Approval(msg.sender, _spender, _value);
    return true;
  }

  function allowance(address _owner, address _spender) public view returns (uint) {
    return allowed[_owner][_spender];
  }

  function increaseApproval(address _spender, uint _addedValue) public whenNotPaused returns (bool) {
	assert(allowed[msg.sender][_spender] + _addedValue >= allowed[msg.sender][_spender]);
    allowed[msg.sender][_spender] = allowed[msg.sender][_spender] + _addedValue;
    emit Approval(msg.sender, _spender, allowed[msg.sender][_spender]);
    return true;
  }

  function decreaseApproval(address _spender, uint _subtractedValue) public whenNotPaused returns (bool) {
    uint oldValue = allowed[msg.sender][_spender];
    if (_subtractedValue > oldValue) {
      allowed[msg.sender][_spender] = 0;
    } else {
	  assert(_subtractedValue <= oldValue);
      allowed[msg.sender][_spender] = oldValue - _subtractedValue;
    }
    emit Approval(msg.sender, _spender, allowed[msg.sender][_spender]);
    return true;
  }
  
}