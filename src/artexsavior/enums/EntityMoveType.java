package artexsavior.enums;

/**
 *
 * @author Peixoto
 */

public enum EntityMoveType {
    STATIC(0),            // Fica parado
    STALKER(1),           // Segue a entidade mais próxima
    OPPOSE_STALKER(2),    // Segue a entidade oposta mais próxima
    FRIEND_STALKER(3),    // Segue a entidade amiga mais próxima   
    PASSIVE(4),           // Anda aleatóriamente, ignorando outras entidades
    AGGRESSIVE(5),        // Somente não ataca entidades de seu próprio tipo
    AGGRESSIVE_ALL(6),    // Ataca todas as entidades
    OPPOSE_AGGRESSIVE(7), // Ataca a entidade oposta mais próxima
    FRIGHTENED(8),        // Foge de todas as entidades    
    CONTROLLED(9),        // Controlado pelo usuário  
    EYE_STALKER(10);      // Fica parado, apenas olhando para o jogador
    
    EntityMoveType(int moveType) {
        this.id = moveType;
    }
    
    private final int id;
    
    public static EntityMoveType getMoveType(int moveType) {
        switch(moveType) {
            case 0:
                return EntityMoveType.STATIC;
            case 1:
                return EntityMoveType.STALKER;    
            case 2:
                return EntityMoveType.OPPOSE_STALKER;
            case 3:
                return EntityMoveType.FRIEND_STALKER;    
            case 4:
                return EntityMoveType.PASSIVE;    
            case 5:
                return EntityMoveType.AGGRESSIVE;    
            case 6:
                return EntityMoveType.AGGRESSIVE_ALL;
            case 7:
                return EntityMoveType.OPPOSE_AGGRESSIVE;
            case 8:
                return EntityMoveType.FRIGHTENED;
            case 9:
                return EntityMoveType.CONTROLLED;
            case 10:
                return EntityMoveType.EYE_STALKER;
            default:
                return EntityMoveType.STATIC;    
        }        
    }
        
}
