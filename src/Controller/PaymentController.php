<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Stripe\Checkout\Session;

use Symfony\Component\Routing\Generator\UrlGeneratorInterface;
use App\Entity\Contrat;
use App\Entity\Voiture;
use App\Repository\ContratRepository;
use App\Repository\VoitureRepository;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Component\HttpFoundation\Request;
use App\Form\ContratType;
use App\Form\VoitureType;
use Stripe\Stripe;
class PaymentController extends AbstractController
{
  #[Route('/checkout', name: 'checkout')]
  public function checkout($stripeSK,Request $request): Response
  {
    //$voiture = new Voiture();
    //$contrat = new Contrat();
    //$voiture->addContrat($contrat);
    //$form = $this->createForm(VoitureType::class, $voiture);
    //$form->handleRequest($request);

    //if ($form->isSubmitted() && $form->isValid()) {
        // Get the price value from the submitted form
        //$prix = $form->get('contrats')->getData()[0]->getPrix();

        // Create the Stripe session with the updated price value
        Stripe::setApiKey($stripeSK);

        $session = Session::create([
            'payment_method_types' => ['card'],
            'line_items'           => [
                [
                    'price_data' => [
                        'currency'     => 'usd',
                        'product_data' => [
                            'name' => 'Assurance Voiture',
                        ],
                        'unit_amount'  => 2000 //$prix * 100, // Convert to cents
                    ],
                    'quantity'   => 1,
                ]
            ],
            'mode'                 => 'payment',
            'success_url'          => $this->generateUrl('success_url', [], UrlGeneratorInterface::ABSOLUTE_URL),
            'cancel_url'           => $this->generateUrl('cancel_url', [], UrlGeneratorInterface::ABSOLUTE_URL),
        ]);

        return $this->redirect($session->url, 303);
    

    return $this->render('contrat/add.html.twig', [
        'f' => $form->createView(),
    ]);
  }

        #[Route('/success-url', name: 'success_url')]
    public function successUrl(): Response
    {
        return $this->render('payment/success.html.twig', []);
    }


    #[Route('/cancel-url', name: 'cancel_url')]
    public function cancelUrl(): Response
    {
        return $this->render('payment/cancel.html.twig', []);
    }
}


        

