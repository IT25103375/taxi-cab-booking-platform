import React, {type ReactNode} from "react";

type PopupSize = 'sm' | 'md' | 'lg' | 'xl' | 'full';

interface ModalProps {
    isOpen: boolean;
    onClose: () => void;
    size: PopupSize;
    children: ReactNode;
}

const maxWidth: Record<PopupSize, string> = {
    sm: 'max-w-sm ',     // 24rem (384px)
    md: 'max-w-md ',     // 28rem (448px)
    lg: 'max-w-lg ',     // 32rem (512px)
    xl: 'max-w-xl ',     // 36rem (576px)
    full: 'max-w-full ', // 100%
};

const Popup: React.FC<ModalProps> = ({ isOpen, onClose, size = 'md', children }) => {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50">
            <div className={maxWidth[size] + 'relative w-full p-6 bg-zinc-900 rounded-lg shadow-xl'}>
                <button
                    onClick={onClose}
                    className="absolute top-1 right-4 text-gray-500 hover:text-gray-700 text-4xl"
                >
                    &times;
                </button>
                {children}
            </div>
        </div>
    );
};

export default Popup;