import React from "react";

const Footer = () => {
  return (
    <footer className="bg-gray-900 text-white py-10">
      <div className="container mx-auto px-6">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Company Info */}
          <div>
            <h2 className="text-2xl font-semibold text-white">Mega CityCab</h2>
            <p className="mt-4 text-gray-400">
              Reliable cab services for a hassle-free journey. Your comfort and safety are our priorities.
            </p>
          </div>

          {/* Quick Links */}
          <div>
            <h2 className="text-xl font-semibold text-white">Quick Links</h2>
            <ul className="mt-4 space-y-2">
              {["Home", "About Us", "Services", "Contact"].map((item, index) => (
                <li key={index}>
                  <a href="#" className="text-gray-400 hover:text-white transition">
                    {item}
                  </a>
                </li>
              ))}
            </ul>
          </div>

          {/* Contact Info */}
          <div>
            <h2 className="text-xl font-semibold text-white">Contact Us</h2>
            <p className="mt-4 text-gray-400">📍 3/6 vellawathe Colombo </p>
            <p className="text-gray-400">📞 +94 11 2525475</p>
            <p className="text-gray-400">✉ office@megacitycab.com</p>
          </div>
        </div>

        <hr className="my-6 border-gray-700" />

        {/* Bottom Footer */}
        <div className="flex flex-col md:flex-row justify-between items-center">
          <p className="text-gray-400">© 2025 MEgaCityCab. All Rights Reserved.</p>
          <div className="flex space-x-4 mt-4 md:mt-0">
            {[ 
              { src: "https://www.svgrepo.com/show/303114/facebook-3-logo.svg", alt: "Facebook" },
              { src: "https://www.svgrepo.com/show/303115/twitter-3-logo.svg", alt: "Twitter" },
              { src: "https://www.svgrepo.com/show/303145/instagram-2-1-logo.svg", alt: "Instagram" },
              { src: "https://www.svgrepo.com/show/28145/linkedin.svg", alt: "LinkedIn" },
            ].map(({ src, alt }, index) => (
              <img key={index} src={src} width="30" height="30" alt={alt} className="hover:opacity-75 cursor-pointer" />
            ))}
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
